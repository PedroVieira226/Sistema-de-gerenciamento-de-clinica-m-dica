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
    private final ExameController controller;
    private static final String ENTIDADE = "Exame";

    public ExameView(ExameController controller) {
        this.controller = controller;
        showMenu();
    }

    public void showMenu() {
        String menu = String.format("""
            1. Adicionar %s
            2. Atualizar %s
            3. Remover %s
            4. Buscar %s
            5. Listar %ss
            6. Sair""", ENTIDADE, ENTIDADE, ENTIDADE, ENTIDADE, ENTIDADE);

        while (true) {
            String option = readString(menu);
            if (option == null) return;

            switch (option) {
                case "1" -> adicionar();
                case "2" -> atualizar();
                case "3" -> remover();
                case "4" -> buscar();
                case "5" -> listar();
                case "6" -> { return; }
                default -> showError("Opção inválida!");
            }
        }
    }

    private void adicionar() {
        String tipo = readString("Tipo do " + ENTIDADE + ":");
        if (tipo == null || tipo.isEmpty()) {
            showError("Tipo é obrigatório!");
            return;
        }

        Date dataPrescricao = readDate("Data de Prescrição");
        if (dataPrescricao == null) return;

        Date dataRealizacao = readDate("Data de Realização");
        if (dataRealizacao == null) return;

        String resultado = readString("Resultado do " + ENTIDADE + ":");
        if (resultado == null) return;

        Double preco = readDouble("Preço do " + ENTIDADE + ":");
        if (preco == null || preco <= 0) {
            showError("Preço deve ser maior que zero!");
            return;
        }

        try {
            controller.create(tipo, dataPrescricao, dataRealizacao, resultado, preco);
            showSuccess(ENTIDADE + " " + SUCESSO_ADICIONAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void atualizar() {
        Integer id = readInt("ID do " + ENTIDADE + " a ser atualizado:");
        if (id == null) return;

        Exame exame = controller.read(id);
        if (exame == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        String tipo = readString("Novo Tipo do " + ENTIDADE + ":");
        if (tipo != null && !tipo.isEmpty()) exame.setTipoDoExame(tipo);

        Date dataPrescricao = readDate("Nova Data de Prescrição");
        if (dataPrescricao != null) exame.setDataDePrescricao(dataPrescricao);

        Date dataRealizacao = readDate("Nova Data de Realização");
        if (dataRealizacao != null) exame.setDataDeRealizacao(dataRealizacao);

        String resultado = readString("Novo Resultado do " + ENTIDADE + ":");
        if (resultado != null) exame.setResultadoDoExame(resultado);

        Double preco = readDouble("Novo Preço do " + ENTIDADE + ":");
        if (preco != null && preco > 0) exame.setPrecoDoExame(preco);

        try {
            controller.update(exame);
            showSuccess(ENTIDADE + " " + SUCESSO_ATUALIZAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void remover() {
        Integer id = readInt("ID do " + ENTIDADE + " a ser removido:");
        if (id == null) return;

        Exame exame = controller.read(id);
        if (exame == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        if (!showConfirmation(CONFIRMAR_REMOCAO)) return;

        try {
            controller.delete(exame);
            showSuccess(ENTIDADE + " " + SUCESSO_REMOVER);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void buscar() {
        Integer id = readInt("ID do " + ENTIDADE + ":");
        if (id == null) return;

        Exame exame = controller.read(id);
        if (exame == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        showMessage(formatarExame(exame));
    }

    private void listar() {
        List<Exame> exames = controller.listAll();
        if (exames.isEmpty()) {
            showMessage("Nenhum " + ENTIDADE + " cadastrado.");
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de " + ENTIDADE + "s:\n\n");
        exames.forEach(exame -> sb.append(formatarExame(exame)).append("\n"));
        showMessage(sb.toString());
    }

    private String formatarExame(Exame exame) {
        return String.format("""
            ID: %d
            Tipo: %s
            Data Prescrição: %s
            Data Realização: %s
            Resultado: %s
            Preço: R$ %.2f""",
                exame.getIdExame(),
                exame.getTipoDoExame(),
                formatDate(exame.getDataDePrescricao()),
                formatDate(exame.getDataDeRealizacao()),
                exame.getResultadoDoExame(),
                exame.getPrecoDoExame());
    }
}