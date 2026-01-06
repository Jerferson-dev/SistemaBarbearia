import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class MenuPrincipal {

    private Agenda agenda;
    private Scanner scanner;

    private DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd MM yy HH:mm");

    public MenuPrincipal() {
        this.agenda = new Agenda();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        agenda.carregarDados();

        boolean executando = true;
        while (executando) {
            exibirOpcoes();
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    uiAgendar();
                    break;
                case "2":
                    agenda.listarPorPeriodo();
                    break;
                case "3":
                    uiRemarcar();
                    break;
                case "4":
                    uiCancelar();
                    break;
                case "5":
                    agenda.gerarRelatorioFinanceiro();
                    break;
                case "6":
                    LocalDate dia = lerData("Qual dia você quer consultar? (dd MM yy): ");
                    if (dia != null) {
                        agenda.mostrarHorariosDisponiveis(dia);
                    }
                    break;
                case "0":
                    agenda.salvarDados();
                    System.out.println("Saindo... Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("❌ Opção inválida!");
            }
            System.out.println();
        }
    }


    private void exibirOpcoes() {
        System.out.println("=== ✂️ BARBEARIA CORTE CHIC ✂️ ===");
        System.out.println("1. Novo Agendamento");
        System.out.println("2. Listar Agendamentos");
        System.out.println("3. Remarcar Horário");
        System.out.println("4. Cancelar Agendamento");
        System.out.println("5. Relatório Financeiro 💰");
        System.out.println("6. Ver Horários Livres 🕰️");
        System.out.println("0. Salvar e Sair");
        System.out.print("👉 Escolha uma opção: ");

    }

    private void uiAgendar() {
        System.out.println("\n--- NOVO AGENDAMENTO ---");
        System.out.print("Nome do Cliente: ");
        String nomeCliente = scanner.nextLine();

        String nomeProfissional = "Marcos";

        System.out.print("Serviço (ex: Corte, Barba): ");
        String nomeServico = scanner.nextLine();

        System.out.print("Valor (ex: 35.50): ");
        String valorTexto = scanner.nextLine();

        BigDecimal valorServico = new BigDecimal(valorTexto.replace(",", "."));

        LocalDateTime dataHora = lerDataHora("Data e Hora (dd MM yy HH:mm): ");

        if (dataHora != null) {
            Cliente c = new Cliente(nomeCliente);
            Profissional p = new Profissional(nomeProfissional);

            Servico s = new Servico(nomeServico,valorServico);

            agenda.agendar(c, p, s, dataHora);
        }
    }

    private void uiRemarcar() {
        System.out.println("\n--- REMARCAR ---");

        LocalDateTime dataAtual = lerDataHora("Digite a data/hora do agendamento ATUAL (dd MM yy HH:mm): ");

        if (dataAtual != null) {
            Agendamento agendamento = agenda.buscarPorHorario(dataAtual);

            if (agendamento != null) {
                System.out.println("Agendamento encontrado para: " + agendamento.getCliente().getName());

                LocalDateTime novaData = lerDataHora("Digite o NOVO horário desejado (dd MM yy HH:mm): ");
                if (novaData != null) {
                    agenda.remarcarAgendamento(agendamento, novaData);
                }
            } else {
                System.out.println("❌ Nenhum agendamento encontrado nesse horário.");
            }
        }
    }

    private void uiCancelar() {
        System.out.println("\n--- CANCELAR ---");
        LocalDateTime dataAtual = lerDataHora("Digite a data/hora do agendamento que deseja cancelar: ");

        if (dataAtual != null) {
            Agendamento agendamento = agenda.buscarPorHorario(dataAtual);

            if (agendamento != null) {
                System.out.println("Cancelando agendamento de " + agendamento.getCliente().getName() + "...");
                agenda.cancelarAgendamento(agendamento.getId());
            } else {
                System.out.println("❌ Nenhum agendamento encontrado nesse horário.");
            }
        }
    }

    // Método para ler data sem quebrar o programa se o usuário errar
    private LocalDateTime lerDataHora(String mensagem) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine();
        try {
            return LocalDateTime.parse(entrada, formatador);
        } catch (DateTimeParseException e) {
            System.out.println("❌ Formato inválido! Use espaços e 2 dígitos pro ano. Ex: 25 01 26 14:30");
            return null;
        }
    }
    // Método para ler apenas DIA/MÊS/ANO
    private LocalDate lerData(String mensagem) {
        System.out.print(mensagem);
        String entrada = scanner.nextLine();
        try {
            // Usa um formatador só de data
            return LocalDate.parse(entrada, java.time.format.DateTimeFormatter.ofPattern("dd MM yy"));
        } catch (java.time.format.DateTimeParseException e) {
            System.out.println("❌ Data inválida! Use: 25 01 26 ");
            return null;
        }
    }


}



