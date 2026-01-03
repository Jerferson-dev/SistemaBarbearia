import java.math.BigDecimal;

public class Servico {
    private String nome; // ex: corte degradê
    private BigDecimal preco; // dica de ouro: para dinheiro em java, evite 'Double', use 'Bigdecimal'
    private int duracaoEmMinutos; // Ex: 45 (Minutos) - Importante para calcular a agenda

    public Servico(String nome, BigDecimal preco, int duracaoEmMinutos) {
        this.nome = nome;
        this.preco = preco;
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
