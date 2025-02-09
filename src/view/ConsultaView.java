package view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import entities.Consulta;
import controller.ConsultaController;
import controller.MedicoController;
import controller.PacienteController;
import controller.PrescricaoController;
import enums.StatusConsulta;

/**
 * Classe para a visualização e interação com Consultas.
 */
public class ConsultaView extends BaseView {

    private ConsultaController consultaController;
    private PrescricaoController prescricaoController;

    /**
     * Construtor para inicializar os controladores necessários e exibir o menu.
     */
    public ConsultaView(ConsultaController consultaController,
                        MedicoController medicoController,
                        PacienteController pacienteController,
                        PrescricaoController prescricaoController) {
        this.consultaController = consultaController;
        this.prescricaoController = prescricaoController;
        showMenu();
    }

    /**
     * Exibe o menu principal para interação com o usuário.
     */
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
            switch (option) {
                case "1":
                    agendarConsulta();
                    break;
                case "2" :
                    atualizarConsulta();
                    break;
                case "3" :
                    cancelarConsulta();
                    break;
                case "4" :
                    buscarConsultaPorId();
                    break;
                case "5" :
                    listarTodasConsultas();
                    break;
                case "6" :
                    adicionarPrescricaoConsulta();
                    break;
                case "7" : { return; }
                default : JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    /**
     * Agenda uma nova consulta solicitando dados ao usuário.
     */
    private void agendarConsulta() {
        try {
            String tipoConsulta = readString("Tipo de Consulta (Especialidade):");
            Date dataConsulta = readDate("Data da Consulta");

            // Lê hora e minuto para criar LocalDateTime
            int hora = Integer.parseInt(readString("Hora da consulta (0-23):"));
            int minuto = Integer.parseInt(readString("Minuto da consulta (0-59):"));

            if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
                throw new IllegalArgumentException("Horário inválido!");
            }

            LocalDateTime horarioInicio = LocalDateTime.now()
                    .withHour(hora)
                    .withMinute(minuto);

            LocalDateTime duracao = horarioInicio.plusMinutes(30); // Duração padrão de 30 minutos
            String cpfPaciente = readString("CPF do Paciente:");
            String cpfMedico = readString("CPF do Médico:");
            double valorConsulta = Double.parseDouble(readString("Valor da Consulta (R$):"));

            // Arrays vazios para medicamentos e exames iniciais
            ArrayList<Integer> medicamentosIds = new ArrayList<>();
            ArrayList<Integer> examesIds = new ArrayList<>();

            consultaController.create(
                    tipoConsulta,
                    dataConsulta,
                    horarioInicio,
                    duracao,
                    cpfPaciente,
                    cpfMedico,
                    valorConsulta,
                    StatusConsulta.AGENDADA,
                    medicamentosIds,
                    examesIds
            );

            JOptionPane.showMessageDialog(null, "Consulta agendada com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, insira um valor numérico válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao agendar consulta: " + e.getMessage());
        }
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