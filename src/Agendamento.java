import java.time.LocalDateTime;

public class Agendamento {

    // Atributos
    private Cliente cliente;
    private Barbeiro Profissional;
    private Servico servico;

    // O localDataTime guarda data e hora juntos (ex: 2026-01-02T14:30:00)
    private LocalDateTime datahora;

    private StatusAgendamento status;
    private MetodoPagamento pagamento;

    public Agendamento(Cliente cliente, Barbeiro profissional, Servico servico, LocalDateTime datahora, StatusAgendamento status) {
        this.cliente = cliente;
        Profissional = profissional;
        this.servico = servico;
        this.datahora = datahora;
        this.status = StatusAgendamento.PENDENTE; //todo agendamento nasce como pendente
    }

    //Método para verificar quando o atendimento acaba
    public void realizarPagamento (){

    }
}
