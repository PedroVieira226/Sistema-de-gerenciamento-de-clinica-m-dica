package view;

import javax.swing.*;
import java.util.List;
import entities.Medicamento;
import controller.MedicamentoController;

/**
 * Classe para a visualização e interação com Medicamentos.
 */
public class MedicamentoView extends BaseView {
    private final MedicamentoController controller;
    private static final String ENTIDADE = "Medicamento";

    public MedicamentoView(MedicamentoController controller) {
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
        String nome = readString("Nome do " + ENTIDADE + ":");
        if (nome == null || nome.isEmpty()) {
            showError("Nome é obrigatório!");
            return;
        }

        try {
            controller.create(nome);
            showSuccess(ENTIDADE + " " + SUCESSO_ADICIONAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void atualizar() {
        Integer id = readInt("ID do " + ENTIDADE + " a ser atualizado:");
        if (id == null) return;

        Medicamento medicamento = controller.read(id);
        if (medicamento == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        String nome = readString("Novo Nome do " + ENTIDADE + ":");
        if (nome == null || nome.isEmpty()) {
            showError("Nome é obrigatório!");
            return;
        }

        medicamento.setNome(nome);

        try {
            controller.update(medicamento);
            showSuccess(ENTIDADE + " " + SUCESSO_ATUALIZAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void remover() {
        Integer id = readInt("ID do " + ENTIDADE + " a ser removido:");
        if (id == null) return;

        Medicamento medicamento = controller.read(id);
        if (medicamento == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        if (!showConfirmation(CONFIRMAR_REMOCAO)) return;

        try {
            controller.delete(medicamento);
            showSuccess(ENTIDADE + " " + SUCESSO_REMOVER);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void buscar() {
        Integer id = readInt("ID do " + ENTIDADE + ":");
        if (id == null) return;

        Medicamento medicamento = controller.read(id);
        if (medicamento == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        showMessage(formatarMedicamento(medicamento));
    }

    private void listar() {
        List<Medicamento> medicamentos = controller.listAll();
        if (medicamentos.isEmpty()) {
            showMessage("Nenhum " + ENTIDADE + " cadastrado.");
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de " + ENTIDADE + "s:\n\n");
        medicamentos.forEach(medicamento -> sb.append(formatarMedicamento(medicamento)).append("\n"));
        showMessage(sb.toString());
    }

    private String formatarMedicamento(Medicamento medicamento) {
        return String.format("""
            ID: %d
            Nome: %s""",
                medicamento.getIdMedicamento(),
                medicamento.getNome());
    }
}