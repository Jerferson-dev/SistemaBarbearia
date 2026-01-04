import java.math.BigDecimal;

public class Servico {
    private String name; // ex: corte degradê
    private BigDecimal preco; // dica de ouro: para dinheiro em java, evite 'Double', use 'Bigdecimal'
    private int duracaoEmMinutos; // Ex: 45 (Minutos) - Importante para calcular a agenda

    public Servico(String nome, BigDecimal preco, int duracaoEmMinutos) {
        this.name = nome;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    public void setDuracaoEmMinutos(int duracaoEmMinutos) {
        this.duracaoEmMinutos = duracaoEmMinutos;
    }
}
