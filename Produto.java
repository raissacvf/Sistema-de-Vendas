package Aula5.Trabalho;

/**
 *
 * @author raiss
 */




public class Produto {

    private String codigo;
    private String nome;
    private double preco;
    private String marca;
    private TipoProduto tipo;

    public Produto(String codigo, String nome, double preco, String marca, TipoProduto tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.marca = marca;
        this.tipo = tipo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getMarca() {
        return this.marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public TipoProduto getTipo() {
        return this.tipo;
    }

    public void setTipo(TipoProduto tipo) {
        this.tipo = tipo;
    }
}
