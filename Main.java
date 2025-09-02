
package Aula5.Trabalho;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Produto> produtos = new ArrayList<>();
    private static List<Vendedor> vendedores = new ArrayList<>();
    private static List<Venda> vendas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarDadosExemplo();
        
        while (true) {
            exibirMenu();
            System.out.print("Escolha uma opção: ");
            try {
                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        cadastrarProduto();
                        break;
                    case 2:
                        realizarVenda();
                        break;
                    case 3:
                        consultarEstoque();
                        break;
                    case 4:
                        visualizarVendedores();
                        break;
                    case 5:
                        gerarRelatorioDiario();
                        break;
                    case 6:
                        System.out.println("Saindo do sistema. Até mais!");
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
    }
    
    private static void inicializarDadosExemplo() {
        // As listas de vendedores e produtos começam vazias.
    }
    
    private static void exibirMenu() {
        System.out.println("\n--- Sistema de Gestão de Loja de Roupas ---");
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Realizar Venda");
        System.out.println("3. Consultar Estoque");
        System.out.println("4. Visualizar Vendedores e Comissões");
        System.out.println("5. Gerar Relatório Diário de Vendas e Comissões");
        System.out.println("6. Sair");
    }
    
    private static void cadastrarProduto() {
        System.out.println("\n--- Cadastro de Produto ---");
        System.out.print("Digite o código do produto: ");
        String codigo = scanner.nextLine();
        
        // --- INÍCIO DA VALIDAÇÃO DO CÓDIGO ---
        // Verifica se o código já existe na lista de produtos.
        if (encontrarProdutoPorCodigo(codigo) != null) {
            System.out.println("Erro: Já existe um produto com este código. Cadastro não realizado.");
            return; // Sai do método se o código não for único.
        }
        // --- FIM DA VALIDAÇÃO ---
        
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o preço do produto: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Digite a marca do produto: ");
        String marca = scanner.nextLine();
        
        System.out.println("Escolha o tipo de produto:");
        TipoProduto[] tipos = TipoProduto.values();
        for (int i = 0; i < tipos.length; i++) {
            System.out.println((i + 1) + ". " + tipos[i].name());
        }
        System.out.print("Opção: ");
        int tipoEscolhido = scanner.nextInt();
        scanner.nextLine();
        TipoProduto tipoProduto = tipos[tipoEscolhido - 1];

        System.out.print("A roupa tem (T)amanho ou (N)umeração? ");
        String tipoRoupa = scanner.nextLine().toUpperCase();
        
        if (tipoRoupa.equals("T")) {
            RoupaComTamanho novaRoupa = new RoupaComTamanho(codigo, nome, preco, marca, tipoProduto);
            System.out.println("Digite a quantidade em estoque para cada tamanho:");
            Tamanho[] tamanhos = Tamanho.values();
            for (Tamanho tamanho : tamanhos) {
                System.out.print("Quantidade para o tamanho " + tamanho.name() + ": ");
                int quantidade = scanner.nextInt();
                novaRoupa.adicionarEstoque(tamanho, quantidade);
            }
            produtos.add(novaRoupa);
        } else if (tipoRoupa.equals("N")) {
            RoupaComNumeracao novaRoupa = new RoupaComNumeracao(codigo, nome, preco, marca, tipoProduto);
            System.out.println("Digite a quantidade em estoque para cada numeração:");
            Numeracao[] numeracoes = Numeracao.values();
            for (Numeracao numeracao : numeracoes) {
                System.out.print("Quantidade para a numeração " + numeracao.name() + ": ");
                int quantidade = scanner.nextInt();
                novaRoupa.adicionarEstoque(numeracao, quantidade);
            }
            produtos.add(novaRoupa);
        } else {
            System.out.println("Opção inválida.");
            return;
        }
        
        System.out.println("Produto '" + nome + "' cadastrado com sucesso!");
    }
    
    private static void realizarVenda() {
        System.out.println("\n--- Realizar Venda ---");
        System.out.print("Digite o nome ou o código do vendedor: ");
        String nomeVendedor = scanner.nextLine();
        
        Vendedor vendedor = null;
        for (Vendedor v : vendedores) {
            if (v.getNome().equalsIgnoreCase(nomeVendedor)) {
                vendedor = v;
                break;
            }
        }
        
        if (vendedor == null) {
            vendedor = new Vendedor(nomeVendedor);
            vendedores.add(vendedor);
            System.out.println("Vendedor '" + nomeVendedor + "' cadastrado para esta venda.");
        }

        List<Produto> itensVenda = new ArrayList<>();
        int opcaoVenda;

        do {
            System.out.print("Digite o código do produto: ");
            String prodId = scanner.nextLine();
            
            Produto produto = encontrarProdutoPorCodigo(prodId);

            if (produto != null) {
                if (produto instanceof RoupaComTamanho) {
                    RoupaComTamanho roupaTamanho = (RoupaComTamanho) produto;
                    System.out.println("Tamanhos disponíveis e estoque:");
                    for (int i = 0; i < roupaTamanho.getTamanhosDisponiveis().size(); i++) {
                        Tamanho tamanho = roupaTamanho.getTamanhosDisponiveis().get(i);
                        int quantidade = roupaTamanho.getEstoquePorTamanho().get(i);
                        System.out.println(tamanho.name() + ": " + quantidade);
                    }
                    System.out.print("Qual tamanho deseja vender? (PP, P, M, G, GG): ");
                    String tamanhoStr = scanner.nextLine().toUpperCase();
                    Tamanho tamanhoVendido = Tamanho.valueOf(tamanhoStr);
                    
                    System.out.print("Quantas unidades deseja vender? ");
                    int quantidadeVendida = scanner.nextInt();
                    scanner.nextLine();

                    if (roupaTamanho.getQuantidadeEmEstoque(tamanhoVendido) >= quantidadeVendida) {
                        for (int i = 0; i < quantidadeVendida; i++) {
                            itensVenda.add(produto);
                        }
                        roupaTamanho.removerDoEstoque(tamanhoVendido, quantidadeVendida);
                        System.out.println(quantidadeVendida + " produto(s) adicionado(s) à venda.");
                    } else {
                        System.out.println("Estoque insuficiente para a quantidade desejada.");
                    }
                } else if (produto instanceof RoupaComNumeracao) {
                    RoupaComNumeracao roupaNumeracao = (RoupaComNumeracao) produto;
                    System.out.println("Numerações disponíveis e estoque:");
                    for (int i = 0; i < roupaNumeracao.getNumeracoesDisponiveis().size(); i++) {
                        Numeracao numeracao = roupaNumeracao.getNumeracoesDisponiveis().get(i);
                        int quantidade = roupaNumeracao.getEstoquePorNumeracao().get(i);
                        System.out.println(numeracao.name() + ": " + quantidade);
                    }
                    System.out.print("Qual numeração deseja vender? (N36, N38, N40, N42, N44): ");
                    String numeracaoStr = scanner.nextLine().toUpperCase();
                    Numeracao numeracaoVendida = Numeracao.valueOf(numeracaoStr);
                    
                    System.out.print("Quantas unidades deseja vender? ");
                    int quantidadeVendida = scanner.nextInt();
                    scanner.nextLine();

                    if (roupaNumeracao.getQuantidadeEmEstoque(numeracaoVendida) >= quantidadeVendida) {
                        for (int i = 0; i < quantidadeVendida; i++) {
                            itensVenda.add(produto);
                        }
                        roupaNumeracao.removerDoEstoque(numeracaoVendida, quantidadeVendida);
                        System.out.println(quantidadeVendida + " produto(s) adicionado(s) à venda.");
                    } else {
                        System.out.println("Estoque insuficiente para a quantidade desejada.");
                    }
                } else {
                    System.out.println("Tipo de produto desconhecido.");
                }
            } else {
                System.out.println("Produto não encontrado.");
            }
            
            System.out.println("\nOpções:");
            System.out.println("1. Adicionar outro produto");
            System.out.println("2. Finalizar venda");
            System.out.print("Escolha uma opção: ");
            opcaoVenda = scanner.nextInt();
            scanner.nextLine();
            
        } while (opcaoVenda == 1);
        
        if (!itensVenda.isEmpty()) {
            Venda novaVenda = new Venda(vendedor, itensVenda);
            vendas.add(novaVenda);
            vendedor.adicionarComissao(novaVenda.getComissao());
            System.out.printf("Venda registrada! Código: %s | Total: R$%.2f, Comissão do Vendedor: R$%.2f%n",
                              novaVenda.getCodigo(), novaVenda.getValorTotal(), novaVenda.getComissao());
        }
    }
    
    private static Produto encontrarProdutoPorCodigo(String codigo) {
        for (Produto produto : produtos) {
            if (produto.getCodigo().equalsIgnoreCase(codigo)) {
                return produto;
            }
        }
        return null;
    }
    
    private static void consultarEstoque() {
        System.out.println("\n--- Estoque de Produtos ---");
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Produto produto : produtos) {
            System.out.printf("Código: %s | Produto: %s | Marca: %s | Preço: R$%.2f%n",
                    produto.getCodigo(), produto.getNome(), produto.getMarca(), produto.getPreco());

            if (produto instanceof RoupaComTamanho) {
                RoupaComTamanho roupaTamanho = (RoupaComTamanho) produto;
                System.out.println("  Estoque por Tamanho:");
                for (int i = 0; i < roupaTamanho.getTamanhosDisponiveis().size(); i++) {
                    Tamanho tamanho = roupaTamanho.getTamanhosDisponiveis().get(i);
                    int quantidade = roupaTamanho.getEstoquePorTamanho().get(i);
                    System.out.printf("    %s: %d%n", tamanho.name(), quantidade);
                }
            } else if (produto instanceof RoupaComNumeracao) {
                RoupaComNumeracao roupaNumeracao = (RoupaComNumeracao) produto;
                System.out.println("  Estoque por Numeração:");
                for (int i = 0; i < roupaNumeracao.getNumeracoesDisponiveis().size(); i++) {
                    Numeracao numeracao = roupaNumeracao.getNumeracoesDisponiveis().get(i);
                    int quantidade = roupaNumeracao.getEstoquePorNumeracao().get(i);
                    System.out.printf("    %s: %d%n", numeracao.name(), quantidade);
                }
            }
        }
    }
    
    private static void visualizarVendedores() {
        System.out.println("\n--- Vendedores e Comissões ---");
        if (vendedores.isEmpty()) {
            System.out.println("Nenhum vendedor cadastrado.");
            return;
        }
        
        for (Vendedor vendedor : vendedores) {
            System.out.printf("Nome: %s | Comissão Acumulada: R$%.2f%n", vendedor.getNome(), vendedor.getComissaoTotal());
        }
    }
    
    private static void gerarRelatorioDiario() {
        System.out.println("\n--- Relatório Diário de Vendas ---");
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada hoje.");
            return;
        }

        double totalVendasDia = 0.0;
        double totalComissaoDia = 0.0;
        java.util.Map<String, Double> comissaoPorVendedor = new java.util.HashMap<>();

        for (Venda venda : vendas) {
            totalVendasDia += venda.getValorTotal();
            totalComissaoDia += venda.getComissao();
            
            String vendedorNome = venda.getVendedor().getNome();
            comissaoPorVendedor.put(vendedorNome, comissaoPorVendedor.getOrDefault(vendedorNome, 0.0) + venda.getComissao());
        }
        
        System.out.printf("Total de Vendas do Dia: R$%.2f%n", totalVendasDia);
        System.out.printf("Total de Comissão do Dia: R$%.2f%n", totalComissaoDia);
        System.out.println("\n--- Comissões por Vendedor no Dia ---");
        
        for (java.util.Map.Entry<String, Double> entry : comissaoPorVendedor.entrySet()) {
            System.out.printf("%s: R$%.2f%n", entry.getKey(), entry.getValue());
        }
    }
}
