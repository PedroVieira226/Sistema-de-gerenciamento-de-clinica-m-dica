package view;

import javax.swing.*;
import java.util.Date;
import java.util.List;

import entities.*;
import controller.PacienteController;
import controller.ConsultaController;
import excptions.CpfJaCadastradoException;

/**
 * Classe para a visualização e interação com Pacientes.
 */
public class PacienteView extends BaseView {

    private PacienteController pacienteController;
    private ConsultaController consultaController;

    /**
     * Construtor para inicializar o controlador de pacientes e exibir o menu.
     *
     * @param pacienteController O controlador de pacientes.
     */
    public PacienteView(PacienteController pacienteController, ConsultaController consultaController) {
        this.pacienteController = pacienteController;
       this.consultaController = consultaController;
        showMenu();
    }

    /**
     * Exibe o menu principal para interação com o usuário.
     */
    public void showMenu() {
        String menu = "1. Adicionar Paciente\n" +
                "2. Atualizar Paciente\n" +
                "3. Remover Paciente\n" +
                "4. Buscar Paciente por CPF\n" +
                "5. Listar todos os Pacientes\n" +
                "6. Detalhes do Paciente\n" + // Nova opção
                "7. Sair";
        while (true) {
            String option = readString(menu);
            switch (option) {
                case "1":
                    addPaciente();
                    break;
                case "2":
                    updatePaciente();
                    break;
                case "3":
                    removePaciente();
                    break;
                case "4":
                    searchPacienteByCpf();
                    break;
                case "5":
                    listAllPacientes();
                    break;
                case "6":
                    showPacienteDetails();
                    break;
                case "7":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    /**
     * Adiciona um novo paciente solicitando dados ao usuário.
     */
    private void addPaciente() {
        String nome = readString("Nome:");
        String cpf = readString("CPF:");
        Date dataNascimento = readDate("Data de Nascimento:");

        try {
            pacienteController.create(nome, cpf, dataNascimento);
            JOptionPane.showMessageDialog(null, "Paciente adicionado com sucesso!");
        } catch (CpfJaCadastradoException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um paciente existente solicitando dados ao usuário.
     */
    private void updatePaciente() {
        String cpf = readString("CPF do paciente a ser atualizado:");
        Paciente paciente = pacienteController.read(cpf);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente não encontrado!");
            return;
        }

        String nome = readString("Novo Nome:");
        Date dataNascimento = readDate("Nova Data de Nascimento:");

        paciente = new Paciente(nome, cpf, dataNascimento);
        pacienteController.update(paciente);

        JOptionPane.showMessageDialog(null, "Paciente atualizado com sucesso!");
    }

    /**
     * Remove um paciente existente solicitando o CPF ao usuário.
     */
    private void removePaciente() {
        String cpf = readString("CPF do paciente a ser removido:");
        Paciente paciente = pacienteController.read(cpf);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente não encontrado!");
            return;
        }

        pacienteController.delete(paciente);
        JOptionPane.showMessageDialog(null, "Paciente removido com sucesso!");
    }

    /**
     * Busca um paciente pelo CPF e exibe suas informações.
     */
    private void searchPacienteByCpf() {
        String cpf = readString("CPF do paciente:");
        Paciente paciente = pacienteController.read(cpf);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente não encontrado!");
        } else {
            JOptionPane.showMessageDialog(null, paciente.toString());
        }
    }
    /**
     * Exibe todos os pacientes e seus IDs usando JOptionPane.
     */
    private void listAllPacientes() {
        List<Paciente> pacientes = pacienteController.listAll();
        if (pacientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum paciente cadastrado.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Pacientes:\n");
            for (Paciente paciente : pacientes) {
                sb.append("ID: ").append(paciente.getIdPaciente())
                        .append(", Nome: ").append(paciente.getNome())
                        .append(", CPF: ").append(paciente.getCpf())
                        .append(", Data de Nascimento: ").append(paciente.getDataDeNascimento()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
    private void showPacienteDetails() {
        String cpf = readString("CPF do paciente:");
        Paciente paciente = pacienteController.read(cpf);
        if (paciente == null) {
            JOptionPane.showMessageDialog(null, "Paciente não encontrado!");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Detalhes do Paciente ===\n")
                .append("Nome: ").append(paciente.getNome()).append("\n")
                .append("CPF: ").append(paciente.getCpf()).append("\n")
                .append("Data de Nascimento: ").append(dateFormat.format(paciente.getDataDeNascimento())).append("\n\n");

        // Busca consultas do paciente
        List<Consulta> consultas = consultaController.listAll();
        boolean hasConsultas = false;

        for (Consulta consulta : consultas) {
            if (consulta.getPacienteDaConsulta().getCpf().equals(cpf)) {
                hasConsultas = true;
                sb.append("Consulta ID: ").append(consulta.getIdConsulta()).append("\n")
                        .append("Data: ").append(dateFormat.format(consulta.getDataDaConsulta())).append("\n")
                        .append("Médico: ").append(consulta.getMedicoDaConsulta().getNome()).append("\n")
                        .append("Especialidade: ").append(consulta.getMedicoDaConsulta().getEspecialidade()).append("\n");

                Prescricao prescricao = consulta.getPrescricaoDaConsulta();
                if (prescricao != null) {
                    sb.append("Exames Prescritos:\n");
                    for (Exame exame : prescricao.getExamesPrescritos()) {
                        sb.append("  - ").append(exame.getTipoDoExame()).append(" (ID: ").append(exame.getIdExame()).append(")\n");
                    }
                    sb.append("Medicamentos Prescritos:\n");
                    for (Medicamento medicamento : prescricao.getMedicamentosPrescritos()) {
                        sb.append("  - ").append(medicamento.getNome()).append(" (ID: ").append(medicamento.getIdMedicamento()).append(")\n");
                    }
                }
                sb.append("\n");
            }
        }

        if (!hasConsultas) {
            sb.append("Nenhuma consulta registrada.\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }
}