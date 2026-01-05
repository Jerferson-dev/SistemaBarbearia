import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Agendamento {

    private UUID id;

    private Cliente cliente;
    private Profissional profissional;
    private Servico servico;
    private LocalDateTime dataHora;


    public Agendamento(Cliente cliente, Profissional profissional, Servico servico, LocalDateTime dataHora) {

        this.id = UUID.randomUUID();
        this.cliente = cliente;
        this.profissional = profissional;
        this.servico = servico;
        this.dataHora = dataHora;
    }

    public Agendamento(UUID id, Cliente cliente, Profissional profissional, Servico servico, LocalDateTime dataHora) {
        this.id = id; // Usa o ID que veio do arquivo
        this.cliente = cliente;
        this.profissional = profissional;
        this.servico = servico;
        this.dataHora = dataHora;
    }

    public UUID getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agendamento that = (Agendamento) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}