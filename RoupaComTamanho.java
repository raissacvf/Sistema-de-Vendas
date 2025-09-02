package Aula5.Trabalho;

/**
 *
 * @author raissa
 */


import java.util.ArrayList;
import java.util.List;

public class RoupaComTamanho extends Produto {

    // Agora usamos duas listas para rastrear o estoque.
    // O índice 0 na lista de tamanhos corresponde ao índice 0 na lista de quantidades.
    private List<Tamanho> tamanhosDisponiveis = new ArrayList<>();
    private List<Integer> estoquePorTamanho = new ArrayList<>();

    public RoupaComTamanho(String codigo, String nome, double preco, String marca, TipoProduto tipo) {
        super(codigo, nome, preco, marca, tipo);
    }
    
    public void adicionarEstoque(Tamanho tamanho, int quantidade) {
        // Encontra a posição do tamanho na lista.
        int index = tamanhosDisponiveis.indexOf(tamanho);
        if (index != -1) {
            // Se o tamanho já existe, atualiza a quantidade.
            int novaQuantidade = estoquePorTamanho.get(index) + quantidade;
            estoquePorTamanho.set(index, novaQuantidade);
        } else {
            // Se o tamanho é novo, adiciona nas duas listas.
            tamanhosDisponiveis.add(tamanho);
            estoquePorTamanho.add(quantidade);
        }
    }
    
    public int getQuantidadeEmEstoque(Tamanho tamanho) {
        int index = tamanhosDisponiveis.indexOf(tamanho);
        if (index != -1) {
            return estoquePorTamanho.get(index);
        }
        return 0; // Retorna 0 se o tamanho não for encontrado.
    }
    
    public void removerDoEstoque(Tamanho tamanho, int quantidade) {
        int index = tamanhosDisponiveis.indexOf(tamanho);
        if (index != -1) {
            int novaQuantidade = estoquePorTamanho.get(index) - quantidade;
            if (novaQuantidade >= 0) {
                estoquePorTamanho.set(index, novaQuantidade);
            } else {
                System.out.println("Erro: Quantidade para remover excede o estoque disponível.");
            }
        } else {
            System.out.println("Erro: Tamanho não encontrado no estoque.");
        }
    }

    public List<Tamanho> getTamanhosDisponiveis() {
        return tamanhosDisponiveis;
    }

    public List<Integer> getEstoquePorTamanho() {
        return estoquePorTamanho;
    }
}
