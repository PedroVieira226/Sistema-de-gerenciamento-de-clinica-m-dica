package view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import entities.Consulta;
import controller.*;
import enums.StatusConsulta;

public class ConsultaView extends BaseView {
    private final ConsultaController consultaController;
    private final MedicoController medicoController;
    private final PacienteController pacienteController;
    private final PrescricaoController prescricaoController;

    public ConsultaView(ConsultaController consultaController,
                        MedicoController medicoController,
                        PacienteController pacienteController,
                        PrescricaoController prescricaoController) {
        // Validação dos controllers
        if (consultaController == null || medicoController == null ||
                pacienteController == null || prescricaoController == null) {
            throw new IllegalArgumentException("Nenhum controller pode ser nulo!");
        }

        this.consultaController = consultaController;
        this.medicoController = medicoController;
        this.pacienteController = pacienteController;
        this.prescricaoController = prescricaoController;
        showMenu();
    }

    public void showMenu() {
        String menu = """
                Sistema de Gestão de Consultas
                ============================
                1. Agendar Consulta
                2. Atualizar Consulta
                3. Cancelar Consulta
                4. Buscar Consulta por ID
                5. Listar todas as Consultas
                6. Adicionar Prescrição à Consulta
                7. Sair
                ============================
                Escolha uma opção:""";

        while (true) {
            String option = readString(menu);
            if (option == null) {
                return; // Usuário clicou em cancelar
            }

            try {
                switch (option) {
                    case "1" -> agendarConsulta();
                    case "2" -> atualizarConsulta();
                    case "3" -> cancelarConsulta();
                    case "4" -> buscarConsultaPorId();
                    case "5" -> listarTodasConsultas();
                    case "6" -> adicionarPrescricaoConsulta();
                    case "7" -> { return; }
                    default -> showError("Opção inválida!");
                }
            } catch (Exception e) {
                showError("Erro inesperado: " + e.getMessage());
            }
        }
    }

    private void agendarConsulta() {
        try {
            // Validação do tipo de consulta
            String tipoConsulta = readString("Tipo de Consulta (Especialidade):");
            if (tipoConsulta == null || tipoConsulta.trim().isEmpty()) {
                showError("Tipo de consulta é obrigatório!");
                return;
            }

            // Validação da data
            Date dataConsulta = readDate("Data da Consulta");
            if (dataConsulta == null) {
                return;
            }

            // Validação se a data não é anterior a hoje
            if (dataConsulta.before(new Date())) {
                showError("A data da consulta não pode ser anterior a hoje!");
                return;
            }

            // Leitura e validação da hora
            String horaStr = readString("Hora da consulta (0-23):");
            if (horaStr == null) return;

            String minutoStr = readString("Minuto da consulta (0-59):");
            if (minutoStr == null) return;

            int hora, minuto;
            try {
                hora = Integer.parseInt(horaStr);
                minuto = Integer.parseInt(minutoStr);
            } catch (NumberFormatException e) {
                showError("Horário inválido! Use apenas números.");
                return;
            }

            if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
                showError("Horário inválido!");
                return;
            }

            // Criação do LocalDateTime combinando data e hora
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataConsulta);
            LocalDateTime horarioInicio = LocalDateTime.of(
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH),
                    hora,
                    minuto
            );

            // Validação do CPF do paciente
            String cpfPaciente = readString("CPF do Paciente (11 dígitos):");
            if (cpfPaciente == null || !validarCPF(cpfPaciente)) {
                return;
            }

            // Validação do CPF do médico
            String cpfMedico = readString("CPF do Médico (11 dígitos):");
            if (cpfMedico == null || !validarCPF(cpfMedico)) {
                return;
            }

            // Validação do valor da consulta
            String valorStr = readString("Valor da Consulta (R$):");
            if (valorStr == null) return;

            double valorConsulta;
            try {
                valorConsulta = Double.parseDouble(valorStr);
                if (valorConsulta < 0) {
                    showError("O valor da consulta não pode ser negativo!");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Valor inválido! Use apenas números e ponto decimal.");
                return;
            }

            // Tenta criar a consulta com tratamento de erro específico
            try {
                consultaController.create(
                        tipoConsulta,
                        dataConsulta,
                        horarioInicio,
                        horarioInicio.plusMinutes(30),
                        cpfPaciente,
                        cpfMedico,
                        valorConsulta,
                        StatusConsulta.AGENDADA,
                        new ArrayList<>(),
                        new ArrayList<>()
                );
                showSuccess("Consulta agendada com sucesso!");
            } catch (Exception e) {
                showError("Erro ao agendar consulta: " + e.getMessage());
            }
        } catch (Exception e) {
            showError("Erro ao processar agendamento: " + e.getMessage());
        }
    }

    private boolean validarCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            showError("CPF deve conter exatamente 11 dígitos numéricos!");
            return false;
        }
        return true;
    }

    /**
     * Atualiza os dados de uma consulta existente.
     */
    private void atualizarConsulta() {
        try {
            Integer id = readInt("ID da consulta a ser atualizada:");
            Consulta consulta = consultaController.read(id);
            if (consulta == null) {
                JOptionPane.showMessageDialog(null, "Consulta não encontrada!");
                return;
            }

            String[] statusOptions = {"AGENDADA", "REALIZADA", "CANCELADA"};
            String novoStatus = (String) JOptionPane.showInputDialog(
                    null,
                    "Selecione o novo status:",
                    "Status da Consulta",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    statusOptions,
                    consulta.getStatusConsultaDaConsulta().toString()
            );

            if (novoStatus != null) {
                consulta.setStatusConsultaDaConsulta(StatusConsulta.valueOf(novoStatus));
            }

            String novoValor = readString("Novo valor da consulta (deixe em branco para manter o atual):");
            if (!novoValor.isEmpty()) {
                double valor = Double.parseDouble(novoValor);
                if (valor < 0) {
                    throw new IllegalArgumentException("O valor não pode ser negativo!");
                }
                consulta.setValorConsulta(valor);
            }

            consultaController.update(consulta);
            JOptionPane.showMessageDialog(null, "Consulta atualizada com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    /**
     * Cancela uma consulta existente.
     */
    private void cancelarConsulta() {
        try {
            Integer id = readInt("ID da consulta a ser cancelada:");
            Consulta consulta = consultaController.read(id);
            if (consulta == null) {
                JOptionPane.showMessageDialog(null, "Consulta não encontrada!");
                return;
            }

            if (consulta.getStatusConsultaDaConsulta() == StatusConsulta.CANCELADA) {
                JOptionPane.showMessageDialog(null, "Esta consulta já está cancelada!");
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(null,
                    "Deseja realmente cancelar esta consulta?\n" +
                            "Data: " + dateFormat.format(consulta.getDataDaConsulta()) + "\n" +
                            "Médico: " + consulta.getMedicoDaConsulta().getNome() + "\n" +
                            "Paciente: " + consulta.getPacienteDaConsulta().getNome(),
                    "Confirmação de Cancelamento",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                consulta.setStatusConsultaDaConsulta(StatusConsulta.CANCELADA);
                consultaController.update(consulta);
                JOptionPane.showMessageDialog(null, "Consulta cancelada com sucesso!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cancelar consulta: " + e.getMessage());
        }
    }

    /**
     * Busca uma consulta pelo ID e exibe suas informações.
     */
    private void buscarConsultaPorId() {
        try {
            Integer id = readInt("ID da consulta:");
            Consulta consulta = consultaController.read(id);
            if (consulta == null) {
                JOptionPane.showMessageDialog(null, "Consulta não encontrada!");
                return;
            }

            exibirDetalhesConsulta(consulta);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar consulta: " + e.getMessage());
        }
    }

    /**
     * Lista todas as consultas cadastradas.
     */
    private void listarTodasConsultas() {
        try {
            var consultas = consultaController.listAll();
            if (consultas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nenhuma consulta cadastrada.");
                return;
            }

            StringBuilder sb = new StringBuilder("Lista de Consultas:\n\n");
            for (Consulta consulta : consultas) {
                exibirDetalhesConsulta(consulta, sb);
                sb.append("\n=========================\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar consultas: " + e.getMessage());
        }
    }

    /**
     * Adiciona uma prescrição a uma consulta existente.
     */
    private void adicionarPrescricaoConsulta() {
        try {
            int idConsulta = readInt("ID da consulta:");
            Consulta consulta = consultaController.read(idConsulta);
            if (consulta == null) {
                JOptionPane.showMessageDialog(null, "Consulta não encontrada!");
                return;
            }

            if (consulta.getStatusConsultaDaConsulta() != StatusConsulta.REALIZADA) {
                JOptionPane.showMessageDialog(null,
                        "Só é possível adicionar prescrição a consultas realizadas!");
                return;
            }

            if (consulta.getPrescricaoDaConsulta() != null) {
                int confirmacao = JOptionPane.showConfirmDialog(null,
                        "Esta consulta já possui uma prescrição. Deseja substituí-la?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (confirmacao != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            ArrayList<Integer> medicamentosIds = new ArrayList<>();
            ArrayList<Integer> examesIds = new ArrayList<>();

            // Coletar IDs dos medicamentos
            while (true) {
                String idMed = readString("ID do Medicamento (ou deixe em branco para finalizar):");
                if (idMed.isEmpty()) break;
                medicamentosIds.add(Integer.parseInt(idMed));
            }

            // Coletar IDs dos exames
            while (true) {
                String idExame = readString("ID do Exame (ou deixe em branco para finalizar):");
                if (idExame.isEmpty()) break;
                examesIds.add(Integer.parseInt(idExame));
            }

            consulta.setPrescricaoDaConsulta(
                    prescricaoController.createFromIds(medicamentosIds, examesIds)
            );

            consultaController.update(consulta);
            JOptionPane.showMessageDialog(null, "Prescrição adicionada com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um ID válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar prescrição: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para exibir detalhes de uma consulta
     */
    private void exibirDetalhesConsulta(Consulta consulta) {
        StringBuilder sb = new StringBuilder();
        exibirDetalhesConsulta(consulta, sb);
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    /**
     * Método auxiliar para formatar detalhes de uma consulta
     */
    private void exibirDetalhesConsulta(Consulta consulta, StringBuilder sb) {
        sb.append("ID: ").append(consulta.getIdConsulta())
                .append("\nTipo: ").append(consulta.getTipoConsulta())
                .append("\nPaciente: ").append(consulta.getPacienteDaConsulta().getNome())
                .append("\nMédico: ").append(consulta.getMedicoDaConsulta().getNome())
                .append("\nData: ").append(dateFormat.format(consulta.getDataDaConsulta()))
                .append("\nHorário: ").append(consulta.getHorarioDeInicio().toLocalTime())
                .append("\nStatus: ").append(consulta.getStatusConsultaDaConsulta())
                .append("\nValor: R$ ").append(String.format("%.2f", consulta.getValorConsulta()));

        if (consulta.getPrescricaoDaConsulta() != null) {
            sb.append("\nPrescrição ID: ").append(consulta.getPrescricaoDaConsulta().getIdPrescricao());
        }
    }
}