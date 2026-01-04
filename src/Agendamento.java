import java.time.LocalDateTime;

public class Agendamento {

    // Atributos
    private Cliente cliente;
    private Profissional profissional;
    private Servico servico;

    // O localDataTime guarda data e hora juntos (ex: 2026-01-02T14:30:00)
    private LocalDateTime dataHora;

    private StatusAgendamento status;
    private MetodoPagamento pagamento;

    public Agendamento(Cliente cliente, Profissional profissional, Servico servico, LocalDateTime datahora) {
        this.cliente = cliente;
        this.profissional = profissional;
        this.servico = servico;
        this.dataHora = datahora;

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

    public StatusAgendamento getStatus() {
        return status;
    }

    public void setStatus(StatusAgendamento status) {
        this.status = status;
    }

    public MetodoPagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(MetodoPagamento pagamento) {
        this.pagamento = pagamento;
    }

    //método para verificar quando o atendimento acaba
    public LocalDateTime calcularHorarioTermino(){
        // pega a hora do agendamento e soma a duração do serviço
        return this.dataHora.plusMinutes(servico.getDuracaoEmMinutos());
    }

    // confirmar pagamento
    public void realizarPagamento(MetodoPagamento metodo){
        this.pagamento = metodo;
        System.out.println("Pagamento resgistrado via " + metodo);

    }
}
