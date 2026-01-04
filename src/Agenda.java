import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Agenda {

    // Lista que guarda todos os agendamentos feitos (seu "Banco de Dados" na memória)
    private List<Agendamento> agendamentos = new ArrayList<>();

    // Tenta salvar um agendamento. Retorna true se deu certo, false se deu erro.
    public boolean agendar(Agendamento novoAgendamento) {

        // 1. Verifica disponibilidade
        boolean estaDisponivel = verificarDisponibilidade(novoAgendamento);

        if (estaDisponivel) {
            agendamentos.add(novoAgendamento);
            System.out.println("✅ Agendamento realizado com sucesso para: " + novoAgendamento.getCliente().getName());
            return true;
        } else {
            System.out.println("❌ Erro: Horário indisponível para este barbeiro!");
            return false;
        }
    }

    // A LÓGICA DE OURO ESTÁ AQUI
    private boolean verificarDisponibilidade(Agendamento novo) {

        // Calcula quando o NOVO agendamento começa e termina
        LocalDateTime novoInicio = novo.getDataHora();
        LocalDateTime novoFim = novo.calcularHorarioTermino(); // Método que criamos na classe Agendamento

        // Varre a lista de agendamentos já marcados
        for (Agendamento existente : agendamentos) {

            // Regra 1: Só importa se for o mesmo barbeiro
            // Se forem barbeiros diferentes, pode agendar no mesmo horário sem problema
            if (existente.getProfissional().getNome().equals(novo.getProfissional().getNome())) {

                LocalDateTime existenteInicio = existente.getDataHora();
                LocalDateTime existenteFim = existente.calcularHorarioTermino();

                // Regra 2: Verifica colisão de horário (A fórmula que expliquei acima)
                // Se (Novo começa antes do Existente acabar) E (Novo termina depois do Existente começar)
                boolean choqueHorario = novoInicio.isBefore(existenteFim) && novoFim.isAfter(existenteInicio);

                if (choqueHorario) {
                    System.out.println("⚠️ Conflito detectado com agendamento das " + existenteInicio.toLocalTime());
                    return false; // Não está disponível!
                }
            }
        }

        return true; // Passou por todos e não bateu com ninguém? Então está livre.
    }


    // Método auxiliar só para você visualizar a agenda no console
    public void imprimirAgenda() {
        System.out.println("\n--- AGENDA DO DIA ---");
        for (Agendamento a : agendamentos) {
            System.out.println("Cliente: " + a.getCliente().getName() +
                    " | Hora: " + a.getDataHora() +
                    " | Serviço: " + a.getServico().getName());
        }
    }
}