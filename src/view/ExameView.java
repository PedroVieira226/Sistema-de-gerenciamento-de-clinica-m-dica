package view;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import entities.Exame;
import controller.ExameController;

/**
 * Classe para a visualização e interação com Exames.
 */
public class ExameView extends BaseView {

    private ExameController exameController;

    /**
     * Construtor para inicializar o controlador de exames e exibir o menu.
     *
     * @param exameController O controlador de exames.
     */
    public ExameView(ExameController exameController) {
        this.exameController = exameController;
        showMenu();
    }

    /**
     * Exibe o menu principal para interação com o usuário.
     */
    public void showMenu() {
        String menu = "1. Adicionar Exame\n" +
                "2. Atualizar Exame\n" +
                "3. Remover Exame\n" +
                "4. Buscar Exame por ID\n" +
                "5. Listar todos os Exames\n" +
                "6. Sair";
        while (true) {
            String option = readString(menu);
            switch (option) {
                case "1":
                    addExame();
                    break;
                case "2":
                    updateExame();
                    break;
                case "3":
                    removeExame();
                    break;
                case "4":
                    searchExameById();
                    break;
                case "5":
                    listAllExames();
                    break;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    /**
     * Adiciona um novo exame solicitando dados ao usuário.
     */
    private void addExame() {
        String tipoDoExame = readString("Tipo do Exame:");
        Date dataDePrescricao = readDate("Data de Prescrição do Exame:");
        Date dataDeRealizacao = readDate("Data de Realização do Exame:");
        String resultadoDoExame = readString("Resultado do Exame:");
        Double precoDoExame = readDoubleInput("Preço do Exame:");

        try {
            exameController.create(tipoDoExame, dataDePrescricao, dataDeRealizacao, resultadoDoExame, precoDoExame);
            JOptionPane.showMessageDialog(null, "Exame adicionado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um exame existente solicitando dados ao usuário.
     */
    private void updateExame() {
        Integer id = readInt("ID do exame a ser atualizado:");
        Exame exame = exameController.read(id);
        if (exame == null) {
            JOptionPane.showMessageDialog(null, "Exame não encontrado!");
            return;
        }

        String tipoDoExame = readString("Novo Tipo do Exame:");
        Date dataDePrescricao = readDate("Nova Data de Prescrição do Exame:");
        Date dataDeRealizacao = readDate("Nova Data de Realização do Exame:");
        String resultadoDoExame = readString("Novo Resultado do Exame:");
        Double precoDoExame = readDoubleInput("Novo Preço do Exame:");

        exame.setTipoDoExame(tipoDoExame);
        exame.setDataDePrescricao(dataDePrescricao);
        exame.setDataDeRealizacao(dataDeRealizacao);
        exame.setResultadoDoExame(resultadoDoExame);
        exame.setPrecoDoExame(precoDoExame);

        exameController.update(exame);

        JOptionPane.showMessageDialog(null, "Exame atualizado com sucesso!");
    }

    /**
     * Remove um exame existente solicitando o ID ao usuário.
     */
    private void removeExame() {
        Integer id = readInt("ID do exame a ser removido:");
        Exame exame = exameController.read(id);
        if (exame == null) {
            JOptionPane.showMessageDialog(null, "Exame não encontrado!");
            return;
        }

        exameController.delete(exame);
        JOptionPane.showMessageDialog(null, "Exame removido com sucesso!");
    }

    /**
     * Busca um exame pelo ID e exibe suas informações.
     */
    private void searchExameById() {
        Integer id = readInt("ID do exame:");
        Exame exame = exameController.read(id);
        if (exame == null) {
            JOptionPane.showMessageDialog(null, "Exame não encontrado!");
        } else {
            JOptionPane.showMessageDialog(null, exame.toString());
        }
    }

    /**
     * Exibe todos os exames e seus detalhes usando JOptionPane.
     */
    private void listAllExames() {
        List<Exame> exames = exameController.listAll();
        if (exames.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum exame cadastrado.");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Lista de Exames:\n");
            for (Exame exame : exames) {
                sb.append("ID: ").append(exame.getIdExame())
                        .append(", Tipo: ").append(exame.getTipoDoExame())
                        .append(", Data de Prescrição: ").append(exame.getDataDePrescricao())
                        .append(", Data de Realização: ").append(exame.getDataDeRealizacao())
                        .append(", Resultado: ").append(exame.getResultadoDoExame())
                        .append(", Preço: ").append(exame.getPrecoDoExame()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
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