package view;

import javax.swing.*;
import java.util.Date;
import java.util.List;

import entities.Paciente;
import controller.PacienteController;
import excptions.CpfJaCadastradoException;

/**
 * Classe para a visualização e interação com Pacientes.
 */
public class PacienteView extends BaseView {

    private PacienteController pacienteController;

    /**
     * Construtor para inicializar o controlador de pacientes e exibir o menu.
     *
     * @param pacienteController O controlador de pacientes.
     */
    public PacienteView(PacienteController pacienteController) {
        this.pacienteController = pacienteController;
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
                "5. Sair";
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
}