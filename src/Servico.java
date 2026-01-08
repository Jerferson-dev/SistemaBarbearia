import java.math.BigDecimal;

public class Servico {
    private String name;
    private BigDecimal valor;
    private int duracaoEmMinutos;

    public Servico(String nome, BigDecimal valor, int duracaoEmMinutos) {
        this.name = nome;
        this.valor = valor;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public Servico(String name) {

        this.name = name;
    }

    public Servico(String nomeServico, BigDecimal valorServico) {
        this.name = nomeServico;
        this.valor = valorServico;
    }


    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public BigDecimal getValor() {

        return valor;
    }
    @Override
    public String toString(){

        return name;
    }

    public void setPreco(BigDecimal preco) {

        this.valor = preco;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public void setDuracaoEmMinutos(int duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }
}
