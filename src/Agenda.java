import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Agenda {
    //lista que guarda todos agendamentos realizados (meu "Banco de dados" na memória)
    private List<Agendamento> agendamentos = new ArrayList<>();

    //tenta salvar um agendamento. retorna true se deu certo, false se deu errado.
    public boolean agendar(Agendamento novoAgendamento){
        boolean estaDisponivel = verificarDisponibilidade(novoAgendamento);

        if (estaDisponivel){
            agendamentos.add(novoAgendamento);
            System.out.println("✅ Agendamento realizado com sucesso para: " + novoAgendamento.getCliente().getNome());
        } else {
            System.out.println("❌ Horário indisponível para este barbeiro!");
            return false;

        }
    }

    // Lógica de ouro esta aqui
    private boolean verificarDisponibilidade(Agendamento novo){

    }
}
