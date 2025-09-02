package Aula5.Trabalho;



import java.util.ArrayList;
import java.util.List;

public class RoupaComNumeracao extends Produto {

    // Usamos duas listas para rastrear o estoque.
    
    private List<Numeracao> numeracoesDisponiveis = new ArrayList<>();
    private List<Integer> estoquePorNumeracao = new ArrayList<>();

    public RoupaComNumeracao(String codigo, String nome, double preco, String marca, TipoProduto tipo) {
        super(codigo, nome, preco, marca, tipo);
    }
    
    
    public void adicionarEstoque(Numeracao numeracao, int quantidade) {
        // Encontra a posição da numeração na lista.
        int index = numeracoesDisponiveis.indexOf(numeracao);//indexOf() para encontrar em qual posição a numeração está na lista 
        if (index != -1) {
            // Se a numeração já existe, atualiza a quantidade.
            int novaQuantidade = estoquePorNumeracao.get(index) + quantidade;
            estoquePorNumeracao.set(index, novaQuantidade);
        } else {
            // Se a numeração é nova, adiciona nas duas listas.
            numeracoesDisponiveis.add(numeracao);
            estoquePorNumeracao.add(quantidade);
        }
    }
    
    
    public int getQuantidadeEmEstoque(Numeracao numeracao) {
        int index = numeracoesDisponiveis.indexOf(numeracao);
        if (index != -1) {
            return estoquePorNumeracao.get(index);
        }
        return 0;
    }
    
   
    public void removerDoEstoque(Numeracao numeracao, int quantidade) {
        int index = numeracoesDisponiveis.indexOf(numeracao);
        if (index != -1) {
            int novaQuantidade = estoquePorNumeracao.get(index) - quantidade;
            if (novaQuantidade >= 0) {
                estoquePorNumeracao.set(index, novaQuantidade);
            } else {
                System.out.println("Erro: Quantidade para remover excede o estoque disponível.");
            }
        } else {
            System.out.println("Erro: Numeração não encontrada no estoque.");
        }
    }

    public List<Numeracao> getNumeracoesDisponiveis() {
        return numeracoesDisponiveis;
    }

    public List<Integer> getEstoquePorNumeracao() {
        return estoquePorNumeracao;
    }
}
