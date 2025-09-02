package Aula5.Trabalho;
import java.util.List;
/**
 *
 * @author raissa
 */
public class Venda {
     private static int contadorVendas = 0; 
    private String codigo;
    private Vendedor vendedor;
    private List<Produto> produtos;
    private double valorTotal;
    private double comissao;

    public Venda(Vendedor vendedor, List<Produto> produtos) {
        // Incrementa o contador e usa o novo valor para gerar o código
        contadorVendas++; 
        this.codigo = "Venda" + contadorVendas; 
        
        this.vendedor = vendedor;
        this.produtos = produtos;
        this.valorTotal = calcularValorTotal();
        this.comissao = calcularComissao();
    }
    
    private double calcularValorTotal() {
        double total = 0.0;
        for (Produto p : this.produtos) {
            total += p.getPreco();
        }
        return total;
    }

    private double calcularComissao() {
        return this.valorTotal * 0.10;
    }
    
    public String getCodigo() { return this.codigo; }
    public Vendedor getVendedor() { return this.vendedor; }
    public double getValorTotal() { return this.valorTotal; }
    public double getComissao() { return this.comissao; }
}
