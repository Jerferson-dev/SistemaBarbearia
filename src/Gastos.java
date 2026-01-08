import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Gastos {
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    public Gastos(String descricao, BigDecimal valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return descricao + " - R$ " + valor;
    }
}
