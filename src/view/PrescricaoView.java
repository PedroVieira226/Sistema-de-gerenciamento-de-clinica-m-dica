package view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import entities.Medicamento;
import entities.Exame;
import entities.Prescricao;
import controller.PrescricaoController;
import controller.MedicamentoController;
import controller.ExameController;

/**
 * Classe para a visualização e interação com Prescrições.
 */
public class PrescricaoView extends BaseView {

    private PrescricaoController prescricaoController;
    private MedicamentoController medicamentoController;
    private ExameController exameController;

    /**
     * Construtor para inicializar o controlador de prescrições e exibir o menu.
     *
     * @param prescricaoController O controlador de prescrições.
     * @param medicamentoController O controlador de medicamentos.
     * @param exameController O controlador de exames.
     */
    public PrescricaoView(PrescricaoController prescricaoController, MedicamentoController medicamentoController, ExameController exameController) {
        this.prescricaoController = prescricaoController;
        this.medicamentoController = medicamentoController;
        this.exameController = exameController;
        showMenu();
    }

    /**
     * Exibe o menu principal para interação com o usuário.
     */
    public void showMenu() {
        String menu = "1. Adicionar Prescrição\n" +
                "2. Atualizar Prescrição\n" +
                "3. Remover Prescrição\n" +
                "4. Buscar Prescrição por ID\n" +
                "5. Listar todas as Prescrições\n" +
                "6. Sair";
        while (true) {
            String option = readString(menu);
            switch (option) {
                case "1":
                    addPrescricao();
                    break;
                case "2":
                    updatePrescricao();
                    break;
                case "3":
                    removePrescricao();
                    break;
                case "4":
                    searchPrescricaoById();
                    break;
                case "5":
                    listAllPrescricoes();
                    break;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    /**
     * Adiciona uma nova prescrição solicitando IDs de medicamentos e nomes de exames ao usuário.
     */
    private void addPrescricao() {
        ArrayList<Medicamento> medicamentosPrescritos = getMedicamentosFromUser();
        ArrayList<Exame> examesPrescritos = getExamesFromUser();

        try {
            prescricaoController.create(medicamentosPrescritos, examesPrescritos);
            JOptionPane.showMessageDialog(null, "Prescrição adicionada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Atualiza os dados de uma prescrição existente solicitando IDs de medicamentos e nomes de exames ao usuário.
     */
    private void updatePrescricao() {
        Integer id = readInt("ID da prescrição a ser atualizada:");
        Prescricao prescricao = prescricaoController.read(id);
        if (prescricao == null) {
            JOptionPane.showMessageDialog(null, "Prescrição não encontrada!");
            return;
        }

        ArrayList<Medicamento> medicamentosPrescritos = getMedicamentosFromUser();
        ArrayList<Exame> examesPrescritos = getExamesFromUser();

        prescricao = new Prescricao(medicamentosPrescritos, examesPrescritos);

        try {
            prescricaoController.update(prescricao);
            JOptionPane.showMessageDialog(null, "Prescrição atualizada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Remove uma prescrição existente solicitando o ID ao usuário.
     */
    private void removePrescricao() {
        Integer id = readInt("ID da prescrição a ser removida:");
        try {
            prescricaoController.delete(id);
            JOptionPane.showMessageDialog(null, "Prescrição removida com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Busca uma prescrição pelo ID e exibe suas informações.
     */
    private void searchPrescricaoById() {
        Integer id = readInt("ID da prescrição:");
        Prescricao prescricao = prescricaoController.read(id);
        if (prescricao == null) {
            JOptionPane.showMessageDialog(null, "Prescrição não encontrada!");
        } else {
            JOptionPane.showMessageDialog(null, prescricao.toString());
        }
    }

    /**
     * Exibe todas as prescrições e seus IDs usando JOptionPane.
     */
    private void listAllPrescricoes() {
        ArrayList<Prescricao> prescricoes = (ArrayList<Prescricao>) prescricaoController.listAll();
        if (prescricoes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhuma prescrição cadastrada.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Prescrições:\n");
            for (Prescricao prescricao : prescricoes) {
                sb.append("ID: ").append(prescricao.getIdPrescricao())
                        .append(", Medicamentos: ").append(prescricao.getMedicamentosPrescritos())
                        .append(", Exames: ").append(prescricao.getExamesPrescritos()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    /**
     * Obtém uma lista de medicamentos a partir dos IDs fornecidos pelo usuário.
     *
     * @return A lista de medicamentos.
     */
    private ArrayList<Medicamento> getMedicamentosFromUser() {
        ArrayList<Medicamento> medicamentosPrescritos = new ArrayList<>();
        String medicamentoIdStr;
        do {
            medicamentoIdStr = readString("ID do Medicamento Prescrito (ou deixe em branco para finalizar):");
            if (!medicamentoIdStr.isEmpty()) {
                Medicamento medicamento = medicamentoController.read(Integer.parseInt(medicamentoIdStr));
                if (medicamento != null) {
                    medicamentosPrescritos.add(medicamento);
                } else {
                    JOptionPane.showMessageDialog(null, "Medicamento não encontrado!");
                }
            }
        } while (!medicamentoIdStr.isEmpty());
        return medicamentosPrescritos;
    }

    /**
     * Obtém uma lista de exames a partir dos nomes fornecidos pelo usuário.
     *
     * @return A lista de exames.
     */
    private ArrayList<Exame> getExamesFromUser() {
        ArrayList<Exame> examesPrescritos = new ArrayList<>();
        String exameNome;
        do {
            exameNome = readString("Nome do Exame Prescrito (ou deixe em branco para finalizar):");
            if (!exameNome.isEmpty()) {
                Date dataPrescricao = new Date();
                Date dataRealizacao = readDate("Data de Realização do Exame:");
                String resultado = readString("Resultado do Exame:");
                Double preco = readDoubleInput("Preço do Exame:");
                Exame exame = new Exame(exameNome, dataPrescricao, dataRealizacao, resultado, preco);
                examesPrescritos.add(exame);
            }
        } while (!exameNome.isEmpty());
        return examesPrescritos;
    }

    /**
     * Lê um valor Double do usuário.
     *
     * @param message A mensagem a ser exibida ao usuário.
     * @return O valor Double inserido pelo usuário.
     */
    private Double readDoubleInput(String message) {
        while (true) {
            try {
                String input = JOptionPane.showInputDialog(message);
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, insira um número.");
            }
        }
    }
}