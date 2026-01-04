import javax.sound.sampled.FloatControl;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // criando dados basicos
        Cliente joao = new Cliente("João","(11)95612-4589");
        Cliente maria = new Cliente("Maria", "(11)95487-5214");

        Profissional barbeiroMarcos = new Profissional("Marcão da Navalha");

        Servico corte = new Servico("Corte Simples", new BigDecimal("50.00"), 30);

        Agenda agendaDoMarcos = new Agenda();

        //criando datas
        //tentando agendar para hoje as 14:00 hrs
       LocalDateTime hora1 = LocalDateTime.of(2026, 1, 2, 14, 0);
       Agendamento agendamento1 = new Agendamento(joao, barbeiroMarcos, corte, hora1);

       // tentando agendar
        agendaDoMarcos.agendar(agendamento1); //deve dar sucesso

        // 3. Tentar criar um conflito!
        // Maria quer agendar às 14:15 (O corte do João vai até 14:30!)
        LocalDateTime hora2 = LocalDateTime.of(2026, 1, 2, 14, 15);
        Agendamento agendamento2 = new Agendamento(maria, barbeiroMarcos, corte, hora2);


        agendaDoMarcos.agendar(agendamento2);

        agendaDoMarcos.imprimirAgenda();



    }
}