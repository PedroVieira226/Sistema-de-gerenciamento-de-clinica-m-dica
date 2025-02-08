package view;

import javax.swing.*;
import java.util.List;
import entities.Medicamento;
import controller.MedicamentoController;

/**
 * Classe para a visualização e interação com Medicamentos.
 */
public class MedicamentoView extends BaseView {

    private MedicamentoController medicamentoController;

    /**
     * Construtor para inicializar o controlador de medicamentos e exibir o menu.
     *
     * @param medicamentoController O controlador de medicamentos.
     */
    public MedicamentoView(MedicamentoController medicamentoController) {
        this.medicamentoController = medicamentoController;
        showMenu();
    }

    /**
     * Exibe o menu principal para interação com o usuário.
     */
    private void showMenu() {
        String menu = "1. Adicionar Medicamento\n" +
                "2. Atualizar Medicamento\n" +
                "3. Remover Medicamento\n" +
                "4. Buscar Medicamento por ID\n" +
                "5. Listar todos os Medicamentos\n" +
                "6. Sair";
        while (true) {
            String option = readString(menu);
            switch (option) {
                case "1":
                    addMedicamento();
                    break;
                case "2":
                    updateMedicamento();
                    break;
                case "3":
                    removeMedicamento();
                    break;
                case "4":
                    searchMedicamentoById();
                    break;
                case "5":
                    listAllMedicamentos();
                    break;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    /**
     * Adiciona um novo medicamento solicitando dados ao usuário.
     */
    private void addMedicamento() {
        String nome = readString("Nome do Medicamento:");

        medicamentoController.create(nome);
        JOptionPane.showMessageDialog(null, "Medicamento adicionado com sucesso!");
    }

    /**
     * Atualiza os dados de um medicamento existente solicitando dados ao usuário.
     */
    private void updateMedicamento() {
        Integer id = readInt("ID do medicamento a ser atualizado:");
        Medicamento medicamento = medicamentoController.read(id);
        if (medicamento == null) {
            JOptionPane.showMessageDialog(null, "Medicamento não encontrado!");
            return;
        }

        String nome = readString("Novo Nome do Medicamento:");

        medicamento = new Medicamento(nome);
        medicamento.setIdMedicamento(id);
        medicamentoController.update(medicamento);

        JOptionPane.showMessageDialog(null, "Medicamento atualizado com sucesso!");
    }

    /**
     * Remove um medicamento existente solicitando o ID ao usuário.
     */
    private void removeMedicamento() {
        Integer id = readInt("ID do medicamento a ser removido:");
        Medicamento medicamento = medicamentoController.read(id);
        if (medicamento == null) {
            JOptionPane.showMessageDialog(null, "Medicamento não encontrado!");
            return;
        }

        medicamentoController.delete(medicamento);
        JOptionPane.showMessageDialog(null, "Medicamento removido com sucesso!");
    }

    /**
     * Busca um medicamento pelo ID e exibe suas informações.
     */
    private void searchMedicamentoById() {
        Integer id = readInt("ID do medicamento:");
        Medicamento medicamento = medicamentoController.read(id);
        if (medicamento == null) {
            JOptionPane.showMessageDialog(null, "Medicamento não encontrado!");
        } else {
            JOptionPane.showMessageDialog(null, medicamento.toString());
        }
    }

    /**
     * Exibe todos os medicamentos e seus IDs usando JOptionPane.
     */
    private void listAllMedicamentos() {
        List<Medicamento> medicamentos = medicamentoController.listAll();
        if (medicamentos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum medicamento cadastrado.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Medicamentos:\n");
            for (Medicamento medicamento : medicamentos) {
                sb.append("ID: ").append(medicamento.getIdMedicamento())
                        .append(", Nome: ").append(medicamento.getNome()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
}