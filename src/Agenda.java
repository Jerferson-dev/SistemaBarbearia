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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.DayOfWeek;


public class Agenda {

    public List<Agendamento> agendamentos;
    public List<Gastos> listaGastos;

    public Agenda() {
        this.agendamentos = new ArrayList<>();
        this.listaGastos = new ArrayList<>();

        carregarGastos();
    }

    public boolean agendar(Cliente cliente, Profissional profissional, Servico servico, LocalDateTime dataHora) {

        if (dataHora.getDayOfWeek() == DayOfWeek.SUNDAY ) {
            System.out.println("🚫 Erro: A barbearia não abre aos Domingos! Escolha outro dia.");
            return false;
        } else if (dataHora.getDayOfWeek() == DayOfWeek.MONDAY){
            System.out.println("🚫 Erro: A barbearia não abre as Segundas! Escolha outro dia.\"");
            return false;
        }

         if (verificarDisponibilidade(dataHora, null)) {
            Agendamento novo = new Agendamento(cliente, profissional, servico, dataHora);
            agendamentos.add(novo);
            System.out.println("✅ Agendamento realizado com sucesso!");
             salvarDados();
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
            salvarDados();
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
                System.out.println(a);
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
            System.out.println("🗑️ Agendamento cancelado com sucesso.");
            salvarDados();
            return true;
        } else {
            System.out.println("❌ Agendamento não encontrado.");
            return false;
        }
    }

    private static final String ARQUIVO_DB = "banco_de_dados.txt";

    //Pega a lista da memória e escreve no banco.de.dados.TXT
    public void salvarDados() {
        try {
            agendamentos.sort(Comparator.comparing(Agendamento::getDataHora));
            StringBuilder conteudo = new StringBuilder();

            for (Agendamento a : agendamentos) {
                conteudo.append(a.getId()).append(";")
                        .append(a.getCliente().getName()).append(";")
                        .append(a.getProfissional().getNome()).append(";")
                        .append(a.getServico().getName()).append(";")
                        .append(a.getServico().getValor()).append(";")
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

    //Lê o banco.de.dados.TXT e preenche a lista na memória
    public void carregarDados() {
        Path caminho = Path.of("banco_de_dados.txt");
        if (!Files.exists(caminho)) {
            System.out.println("⚠️ Arquivo de banco de dados não encontrado (primeira execução?).");
            return;
        }

        try {
            List<String> linhas = Files.readAllLines(caminho);
            agendamentos.clear();

            for (String linha : linhas) {
                try {
                    String[] partes = linha.split(";");

                    // após o debug imprime a linha que está tentando ler para você ver se está quebrada

                    if (partes.length >= 6) {
                        UUID id = UUID.fromString(partes[0]);
                        Cliente c = new Cliente(partes[1]);
                        Profissional p = new Profissional(partes[2]);
                        Servico s = new Servico(partes[3], new BigDecimal(partes[4]));
                        LocalDateTime data = LocalDateTime.parse(partes[5]);

                        Agendamento a = new Agendamento(id, c, p, s, data);

                        agendamentos.add(a);
                    } else {
                        System.out.println("⚠️ Linha ignorada (formato inválido): " + linha);
                    }
                } catch (Exception eInterno) {
                    System.out.println("❌ Erro ao ler linha específica: " + linha);
                    System.out.println("Motivo: " + eInterno.getMessage());
                    eInterno.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.err.println("Erro fatal ao ler arquivo: " + e.getMessage());
        }
    }

    public void listarPorPeriodo() {
        if (agendamentos.isEmpty()) {
            System.out.println("📭 Nenhum agendamento encontrado.");
            return;
        }
        agendamentos.sort(Comparator.comparing(Agendamento::getDataHora));
        DateTimeFormatter fmtMes = DateTimeFormatter.ofPattern("yyyy 'de' MMMM", new Locale("pt", "BR"));
        DateTimeFormatter fmtDia = DateTimeFormatter.ofPattern("dd/MM (EEEE)", new Locale("pt", "BR"));
        DateTimeFormatter fmtHora = DateTimeFormatter.ofPattern("HH:mm");

        NumberFormat fmtDinheiro = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        String ultimoMes = "";
        String ultimoDia = "";

        System.out.println("=== 📅 AGENDAMENTOS ===");

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

    public void mostrarHorariosDisponiveis(LocalDate data) {
        System.out.println("=== 🕰️ DISPONIBILIDADE PARA ===" +
                data.format(java.time.format.DateTimeFormatter.ofPattern("dd MM yy")) + " ===");

        if (data.getDayOfWeek() == DayOfWeek.SUNDAY) {
            System.out.println("🚫 A barbearia está fechada aos Domingos!");
            System.out.println("======================================");
            return;
        }
        LocalTime horarioAtual = LocalTime.of(8, 0);
        LocalTime horarioFechamento = LocalTime.of(19, 0);

        boolean encontrouAlgumLivre = false;
        while (!horarioAtual.isAfter(horarioFechamento)) {
            LocalDateTime dataHoraCheck = data.atTime(horarioAtual);
            if (dataHoraCheck.isBefore(LocalDateTime.now())) {
                horarioAtual = horarioAtual.plusMinutes(30);
                continue;
            }
            boolean isLivre = verificarDisponibilidade(dataHoraCheck, null);
            if (isLivre) {
                System.out.println("✅ " + horarioAtual + " - Livre");
                encontrouAlgumLivre = true;
            }
            horarioAtual = horarioAtual.plusMinutes(30);
        }
        if (!encontrouAlgumLivre) {
            System.out.println("😔 Nenhum horário vago para este dia (ou o dia já acabou).");
        }
        System.out.println("======================================");
    }
    public void gerarRelatorioFinanceiro() {
        System.out.println("=== 💰 BALANÇO FINANCEIRO MENSAL ===");

        Map<YearMonth, BigDecimal> entradas = new TreeMap<>();
        Map<YearMonth, BigDecimal> saidas = new TreeMap<>();
        Map<YearMonth, Boolean> mesesRegistrados = new TreeMap<>();

        //  Somar Entradas
        for (Agendamento a : agendamentos) {
            YearMonth mes = YearMonth.from(a.getDataHora());
            entradas.merge(mes, a.getServico().getValor(), BigDecimal::add);
            mesesRegistrados.put(mes, true);
        }

        //  Somar Saídas (Gastos)
        for (Gastos g : listaGastos) {
            YearMonth mes = YearMonth.from(g.getData());
            saidas.merge(mes, g.getValor(), BigDecimal::add);
            mesesRegistrados.put(mes, true);
        }
        NumberFormat fmt = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DateTimeFormatter fmtMes = DateTimeFormatter.ofPattern("MMMM/yyyy", new Locale("pt", "BR"));

        for (YearMonth mes : mesesRegistrados.keySet()) {
            BigDecimal totalEntrada = entradas.getOrDefault(mes, BigDecimal.ZERO);
            BigDecimal totalSaida = saidas.getOrDefault(mes, BigDecimal.ZERO);
            BigDecimal lucro = totalEntrada.subtract(totalSaida);

            System.out.println("📅 " + mes.format(fmtMes).toUpperCase());
            System.out.println("   🟢 Entradas: " + fmt.format(totalEntrada));
            System.out.println("   🔴 Saídas:   " + fmt.format(totalSaida));

            if (lucro.compareTo(BigDecimal.ZERO) >= 0) {
                System.out.println("   🏆 LUCRO:    " + fmt.format(lucro));
            } else {
                System.out.println("   ⚠️ PREJUÍZO: " + fmt.format(lucro));
            }
            System.out.println("--------------------------------------");
        }
    }
    public void adicionarGasto(String descricao, BigDecimal valor, LocalDate data) {
        Gastos g = new Gastos(descricao, valor, data);
        listaGastos.add(g);
        System.out.println("💸 Gasto registrado: " + descricao);
        salvarGastos();
    }
    private void salvarGastos() {
        Path caminho = Path.of("gastos.txt");
        List<String> linhas = new ArrayList<>();
        for (Gastos g : listaGastos) {
            linhas.add(g.getDescricao() + ";" + g.getValor() + ";" + g.getData());
        }
        try {
            Files.write(caminho, linhas);
        } catch (IOException e) {
            System.err.println("Erro ao salvar gastos: " + e.getMessage());
        }
    }
    private void carregarGastos() {
        Path caminho = Path.of("gastos.txt");
        if (!Files.exists(caminho)) return;

        try {
            List<String> linhas = Files.readAllLines(caminho);
            listaGastos.clear();
            for (String linha : linhas) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length >= 3) {
                    String desc = partes[0];
                    BigDecimal valor = new BigDecimal(partes[1]);
                    LocalDate data = LocalDate.parse(partes[2]);
                    listaGastos.add(new Gastos(desc, valor, data));
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Erro ao ler arquivo de gastos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Arquivo de gastos corrompido. Verifique o formato.");
        }
    }

    public void listarDespesasDetalhadas() {
        if (listaGastos.isEmpty()) {
            System.out.println("📭 Nenhuma despesa registrada até o momento.");
            return;
        }

        System.out.println("=== 💸 HISTÓRICO DE DESPESAS ===");

        NumberFormat fmtDinheiro = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        DateTimeFormatter fmtData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Gastos g : listaGastos) {
            System.out.println("📅 " + g.getData().format(fmtData) +
                    " | " + g.getDescricao().toUpperCase() +
                    " | 🔻 " + fmtDinheiro.format(g.getValor()));
        }
        System.out.println("================================");
    }

}