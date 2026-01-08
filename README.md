# ✂️ Barbearia System - Gerenciamento em Java Puro

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Status](https://img.shields.io/badge/Status-Concluído-green?style=for-the-badge)

Uma solução de agendamento e gestão financeira para barbearias em operação real. Desenvolvido 100% em **Java Core**, o sistema é multiplataforma (Desktop e Mobile via Termux) e foca na aplicação sólida de Orientação a Objetos e lógica de negócios complexa, sem a abstração de frameworks.

---

## 📸 Demonstração

<img width="1671" height="679" alt="Captura de tela console" src="https://github.com/user-attachments/assets/cf47ad1e-0cb2-4597-aa96-704151196a69" />


---

## 🚀 Funcionalidades Principais

### 📅 Gestão de Agendamentos 
- **Agendar:** Criação de novos horários com verificação automática de conflitos (não permite dois clientes no mesmo horário).
- **Consultar:** Listagem inteligente organizada visualmente por **Mês e Dia**.
- **Remarcar:** Alteração de horário simples e rápida.
- **Cancelar:** Liberação imediata da agenda.

### 🧠 Regras de Negócio Inteligentes
- **Trava de Domingos:** O sistema bloqueia automaticamente tentativas de agendamento ou remarcação aos domingos.
- **Validação de Horário:** Só permite agendamentos entre **08:00 e 19:00**.
- **Verificador de Disponibilidade:** Exibe apenas os horários livres do dia (intervalos de 30 min), ocultando horários passados ou ocupados.
- **Auto-Save:** 💾 Todos os dados são salvos automaticamente no disco a cada ação (Agendar, Cancelar, etc). Não é necessário "salvar antes de sair".

### 💰 Gestão Financeira
- **Relatório Mensal e Total:** Agrupa e soma o faturamento por mês (ex: JANEIRO/2026) e total arrecadado até o momento da consulta.
- **Precisão Monetária:** Uso de `BigDecimal` para evitar erros de arredondamento em cálculos financeiros.

### 📉 Controle de Despesas 
- **Registro de Gastos:** Cadastro de contas (luz, água, produtos) para controle de saída.
- **Cálculo de Lucro Líquido:** O relatório financeiro agora deduz as despesas do faturamento bruto, mostrando o lucro real do mês.
- **Histórico Detalhado:** Listagem completa de todas as saídas com data e descrição.
 
---

## 🛠️ Tecnologias e Conceitos Aplicados

Este projeto foi construído para demonstrar domínio sobre a linguagem Java, fugindo de abstrações prontas para entender como as coisas funcionam "por baixo do capô".

- **Java 21+**
- **POO (Programação Orientada a Objetos):** Encapsulamento, Coesão e Associação entre Classes (`Cliente`, `Profissional`, `Servico`, `Agendamento`, `MenuPrincipal`, `Gastos`).
- **Regras de Negócio:** Validações para impedir agendamentos em dias de folga (Domingo/Segunda) ou horários duplicados.
- **Estruturas de Controle:** Loops (`while`, `for`) e condicional (`if/else`, `switch case`) para navegação no menu.
- **Java NIO (New I/O):** Persistência de dados em arquivo `.txt` (formato CSV customizado).
- **Java Time API:** Uso pesado de `LocalDate`, `LocalTime`, `YearMonth` e `DayOfWeek` para manipulação temporal.
- **Collections Framework:** Uso de `ArrayList` para manipulação em memória e `TreeMap` para ordenação de relatórios.
- **UUID:** Identificadores únicos para garantir integridade dos agendamentos.

---
## 📲 Implantação e Caso de Uso Real

Este projeto foi desenvolvido para atender uma demanda real de uma barbearia. 

**O Desafio:**
O profissional precisava gerenciar a agenda de forma rápida, mas não utiliza computadores durante o atendimento, apenas o smartphone.

**A Solução:**
A aplicação foi implantada no ambiente Android utilizando o **Termux** (Emulador de Terminal Linux). Isso permite que o barbeiro execute o backend Java diretamente no celular, com persistência de dados local, sem depender de conexão constante com a internet ou servidores externos.

### ⚙️ Como rodar no Termux (Android)
Caso queira testar no celular:

1. Instale o app [Termux](https://f-droid.org/packages/com.termux/).
2. Atualize os pacotes e instale o Java (confirme com 'y' se pedir):
   ## Execute um comando por vez
   ```bash
   pkg update && pkg upgrade
   ```
   ```bash
   pkg install openjdk-21
   ```
   ```bash
   pkg install git
   ```
   
4. Clone e execute.
   ```bash
   git clone https://github.com/Jerferson-dev/SistemaBarbearia.git
   ```
   ```bash
   cd SistemaBarbearia
   ```
   ```bash
   java Main.java
   ```

---

## ⚙️ Como rodar o projeto

### Pré-requisitos
- Ter o **Java (JDK 21 ou superior)** instalado.

### Passo a passo
1. Clone este repositório:
   ```bash
   git clone https://github.com/Jerferson-dev/SistemaBarbearia.git
   
2. Acesse a pasta do projeto e compile:
   ```bash
   cd SistemaBarbearia
   ```
   ```bash
   java Main.java
   ```


4. Execute a aplicação:
   ```bash
   java Main

---

## 📝 Guia de Uso
O sistema foi otimizado para agilidade no dia a dia.

Formato de Datas
Para agilizar a digitação, o sistema aceita datas com espaços e ano com 2 dígitos:

Correto: 25 01 26 (significa 25/01/2026).
Correto com hora: 25 01 26 14:30.

 ---
 
📱 Menu Principal  

=== ✂️ BARBEARIA SYSTEM ✂️ ===
1. Novo Agendamento      -> Marca um horário (nome do profissional automático).
2. Ver Horários Livres   -> Lista vagas de 30 em 30 min (exclui domingos e segundas).
3. Listar Agendamentos   -> Mostra a agenda organizada cronologicamente.
4. Remarcar Horário      -> Troca o horário de um cliente.
5. Cancelar Agendamento  -> Remove o registro.
6. Relatório Financeiro  -> Mostra quanto a barbearia faturou por mês.
7. Registrar Gasto       -> Registra uma despesa especifica.
8. Listar Todas as Despesas -> Mostra a lista completa de gastos.
0. Salvar e Sair
