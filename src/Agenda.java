import java.time.YearMonth;
import java.util.Map;
import java.util.TreeMap;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Agenda {

    public List<Agendamento> agendamentos;

    public Agenda() {
        this.agendamentos = new ArrayList<>();
    }

    public boolean agendar(Cliente cliente, Profissional profissional, Servico servico, LocalDateTime dataHora) {
        // Passei null no ID para ignorar, pois é um agendamento novo (ninguém para ignorar)
        if (verificarDisponibilidade(dataHora, null)) {
            Agendamento novo = new Agendamento(cliente, profissional, servico, dataHora);
            agendamentos.add(novo);
            System.out.println("✅ Agendamento realizado com sucesso!");
            return true;
        }
        System.out.println("❌ Horário ocupado.");
        return false;
    }

    public boolean remarcarAgendamento(Agendamento agendamentoAntigo, LocalDateTime novoHorario) {

        boolean isLivre = verificarDisponibilidade(novoHorario, agendamentoAntigo.getId());

        if (isLivre) {
            Agendamento novoAgendamento = new Agendamento(
                    agendamentoAntigo.getCliente(),
                    agendamentoAntigo.getProfissional(),
                    agendamentoAntigo.getServico(),
                    novoHorario
            );

            agendamentos.remove(agendamentoAntigo);
            agendamentos.add(novoAgendamento);

            System.out.println("🔄 Reagendado com sucesso para: " + novoHorario);
            return true;
        } else {
            System.out.println("❌ Não foi possível reagendar. O horário " + novoHorario + " já está ocupado.");
            return false;
        }
    }

    /**
     * Verifica se um horário está livre.
     * para dataHora o horário que quero checar.
     * para idIgnorar (Opcional) O ID de um agendamento que deve ser pulado na checagem.
     * Útil para remarcação (não conflitar consigo mesmo).
     */
    private boolean verificarDisponibilidade(LocalDateTime dataHora, UUID idIgnorar) {
        for (Agendamento a : agendamentos) {

            if (idIgnorar != null && a.getId().equals(idIgnorar)) {
                continue;
            }
            if (a.getDataHora().equals(dataHora)) {
                return false;
            }
        }
        return true;
    }

    public void listarAgendamentos() {
        System.out.println("--- Lista de Agendamentos ---");
        if (agendamentos.isEmpty()) {
            System.out.println("Nenhum agendamento.");
        } else {
            for (Agendamento a : agendamentos) {
                System.out.println(a); // Vai usar aquele toString() bonito que criamos
            }
        }
        System.out.println("-----------------------------");
    }
    public Agendamento buscarPorHorario(LocalDateTime dataHora) {
        for (Agendamento a : agendamentos) {
            if (a.getDataHora().equals(dataHora)) {
                return a;
            }
        }
        return null;
    }
    public boolean cancelarAgendamento(UUID idParaCancelar) {
        boolean removeu = agendamentos.removeIf(a -> a.getId().equals(idParaCancelar));
        if (removeu) {
            System.out.println("🗑️ Agendamento cancelado (removido) com sucesso.");
            return true;
        } else {
            System.out.println("❌ Agendamento não encontrado para cancelar.");
            return false;
        }
    }

    private static final String ARQUIVO_DB = "banco_de_dados.txt";
    //Pega a lista da memória e escreve no TXT
    public void salvarDados() {
        try {
            agendamentos.sort(Comparator.comparing(Agendamento::getDataHora));
            StringBuilder conteudo = new StringBuilder();

            for (Agendamento a : agendamentos) {
                conteudo.append(a.getId()).append(";")
                        .append(a.getCliente().getName()).append(";")
                        .append(a.getProfissional().getNome()).append(";")
                        .append(a.getServico().getName()).append(";")
                        .append(a.getServico().getValor()).append(";") // <--- SALVA O VALOR AQUI
                        .append(a.getDataHora())
                        .append(System.lineSeparator());
            }

            Files.writeString(Path.of(ARQUIVO_DB), conteudo.toString(),
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("💾 Dados salvos com sucesso.");

        } catch (IOException e) {
            System.err.println("❌ Erro ao salvar: " + e.getMessage());
        }
    }
    //Lê o TXT e preenche a lista na memória
    public void carregarDados() {
        Path caminho = Path.of(ARQUIVO_DB);
        if (!Files.exists(caminho)) return;
        try {
            List<String> linhas = Files.readAllLines(caminho);
            agendamentos.clear();
            for (String linha : linhas) {
                String[] partes = linha.split(";");

                if (partes.length == 6) {
                    UUID id = UUID.fromString(partes[0]);
                    Cliente c = new Cliente(partes[1]);
                    Profissional p = new Profissional(partes[2]);
                    BigDecimal valor = new BigDecimal(partes[4]);
                    Servico s = new Servico(partes[3], valor);
                    LocalDateTime data = LocalDateTime.parse(partes[5]);
                    Agendamento a = new Agendamento(id, c, p, s, data);
                    agendamentos.add(a);
                }
            }
            System.out.println("📂 Dados carregados.");
        } catch (IOException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    public void listarPorPeriodo() {
        if (agendamentos.isEmpty()) {
            System.out.println("📭 Nenhum agendamento encontrado.");
            return;
        }
        agendamentos.sort(Comparator.comparing(Agendamento::getDataHora));
        DateTimeFormatter fmtMes = DateTimeFormatter.ofPattern("MMMM 'de' yyyy", new Locale("pt", "BR"));
        DateTimeFormatter fmtDia = DateTimeFormatter.ofPattern("dd/MM (EEEE)", new Locale("pt", "BR"));
        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");

        NumberFormat fmtDinheiro = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        String ultimoMes = "";
        String ultimoDia = "";

        System.out.println("=== 📅 AGENDA COM VALORES ===");

        for (Agendamento a : agendamentos) {
            String mesAtual = a.getDataHora().format(fmtMes).toUpperCase();
            String diaAtual = a.getDataHora().format(fmtDia);

            if (!mesAtual.equals(ultimoMes)) {
                System.out.println("\n📦 " + mesAtual);
                System.out.println("   -------------------------");
                ultimoMes = mesAtual;
                ultimoDia = "";
            }

            if (!diaAtual.equals(ultimoDia)) {
                System.out.println("   👉 " + diaAtual);
                ultimoDia = diaAtual;
            }


            BigDecimal valor = a.getServico().getValor();

            String valorFormatado = (valor != null) ? fmtDinheiro.format(valor) : "R$ 0,00";

            System.out.println("      ⏰ " + a.getDataHora().format(fmtHora) +
                    " | " + a.getCliente().getName() +
                    " (" + a.getServico().getName() + " - " + valorFormatado + ")");
        }
        System.out.println("============================");
    }

    public void gerarRelatorioFinanceiro() {
        if (agendamentos.isEmpty()) {
            System.out.println("📭 Nenhum dado para calcular.");
            return;
        }

        Map<YearMonth, BigDecimal> faturamento = new TreeMap<>();

        for (Agendamento a : agendamentos) {
            YearMonth mesAno = YearMonth.from(a.getDataHora());
            BigDecimal valorServico = a.getServico().getValor();
            faturamento.merge(mesAno, valorServico, BigDecimal::add);
        }

        System.out.println("=== 💰 RELATÓRIO FINANCEIRO MENSAL ===");

        NumberFormat fmtDinheiro = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DateTimeFormatter fmtMes = DateTimeFormatter.ofPattern("MMMM/yyyy", new Locale("pt", "BR"));

        BigDecimal totalGeral = BigDecimal.ZERO;

        for (Map.Entry<YearMonth, BigDecimal> entrada : faturamento.entrySet()) {
            YearMonth mes = entrada.getKey();
            BigDecimal totalMes = entrada.getValue();

            System.out.println("📅 " + mes.format(fmtMes).toUpperCase() +
                    ": " + fmtDinheiro.format(totalMes));

            totalGeral = totalGeral.add(totalMes);
        }
        System.out.println("--------------------------------------");
        System.out.println("🏆 TOTAL ARRECADADO NA HISTÓRIA: " + fmtDinheiro.format(totalGeral));
        System.out.println("======================================");
    }
}