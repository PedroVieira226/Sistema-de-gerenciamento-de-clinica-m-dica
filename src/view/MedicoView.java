package view;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import entities.Medico;
import controller.MedicoController;

/**
 * Classe para a visualização e interação com Médicos.
 */
public class MedicoView extends BaseView {

    private MedicoController medicoController;

    /**
     * Construtor para inicializar o controlador de médicos e exibir o menu.
     *
     * @param medicoController O controlador de médicos.
     */
    public MedicoView(MedicoController medicoController) {
        this.medicoController = medicoController;
        showMenu();
    }

    /**
     * Exibe o menu principal para interação com o usuário.
     */
    public void showMenu() {
        String menu = "1. Adicionar Médico\n" +
                "2. Atualizar Médico\n" +
                "3. Remover Médico\n" +
                "4. Buscar Médico por CPF\n" +
                "5. Listar todos os Médicos\n" +
                "6. Sair";
        while (true) {
            String option = readString(menu);
            switch (option) {
                case "1":
                    addMedico();
                    break;
                case "2":
                    updateMedico();
                    break;
                case "3":
                    removeMedico();
                    break;
                case "4":
                    searchMedicoByCpf();
                    break;
                case "5":
                    listAllMedicos();
                    break;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    /**
     * Adiciona um novo médico solicitando dados ao usuário.
     */
    private void addMedico() {
        String nome = readString("Nome do Médico:");
        String cpf = readString("CPF do Médico:");
        Date dataNascimento = readDate("Data de Nascimento do Médico:");
        String crm = readString("CRM do Médico:");
        String especialidade = readString("Especialidade do Médico:");

        try {
            medicoController.create(nome, cpf, dataNascimento, crm, especialidade);
            JOptionPane.showMessageDialog(null, "Médico adicionado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um médico existente solicitando dados ao usuário.
     */
    private void updateMedico() {
        String cpf = readString("CPF do médico a ser atualizado:");
        Medico medico = medicoController.read(cpf);
        if (medico == null) {
            JOptionPane.showMessageDialog(null, "Médico não encontrado!");
            return;
        }

        String nome = readString("Novo Nome do Médico:");
        Date dataNascimento = readDate("Nova Data de Nascimento do Médico:");
        String crm = readString("Novo CRM do Médico:");
        String especialidade = readString("Nova Especialidade do Médico:");

        medico.setNome(nome);
        medico.setDataDeNascimento(dataNascimento);
        medico.setCrm(crm);
        medico.setEspecialidade(especialidade);

        medicoController.update(medico);

        JOptionPane.showMessageDialog(null, "Médico atualizado com sucesso!");
    }

    /**
     * Remove um médico existente solicitando o CPF ao usuário.
     */
    private void removeMedico() {
        String cpf = readString("CPF do médico a ser removido:");
        Medico medico = medicoController.read(cpf);
        if (medico == null) {
            JOptionPane.showMessageDialog(null, "Médico não encontrado!");
            return;
        }

        medicoController.delete(medico);
        JOptionPane.showMessageDialog(null, "Médico removido com sucesso!");
    }

    /**
     * Busca um médico pelo CPF e exibe suas informações.
     */
    private void searchMedicoByCpf() {
        String cpf = readString("CPF do médico:");
        Medico medico = medicoController.read(cpf);
        if (medico == null) {
            JOptionPane.showMessageDialog(null, "Médico não encontrado!");
        } else {
            JOptionPane.showMessageDialog(null, medico.toString());
        }
    }

    /**
     * Exibe todos os médicos e seus IDs usando JOptionPane.
     */
    private void listAllMedicos() {
        List<Medico> medicos = medicoController.listAll();
        if (medicos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum médico cadastrado.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Médicos:\n");
            for (Medico medico : medicos) {
                sb.append("CPF: ").append(medico.getCpf())
                        .append(", Nome: ").append(medico.getNome())
                        .append(", CRM: ").append(medico.getCrm())
                        .append(", Especialidade: ").append(medico.getEspecialidade()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
}