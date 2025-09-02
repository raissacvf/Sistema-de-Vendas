package Aula5.Trabalho;

/**
 *
 * @author raissa
 */

public class Vendedor {
    private String nome;
    private double comissaoTotal;

    // O construtor agora só precisa do nome.
    public Vendedor(String nome) {
        this.setNome (nome);
        this.comissaoTotal = 0.0;
    }

    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    
   
    public double getComissaoTotal() { return this.comissaoTotal; }
    public void setComissaoTotal(double comissaoTotal) { this.comissaoTotal = comissaoTotal; }
    
    public void adicionarComissao(double valorComissao) {
        this.comissaoTotal += valorComissao;
    }
}
