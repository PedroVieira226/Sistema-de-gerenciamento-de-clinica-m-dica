package view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import entities.*;
import controller.PrescricaoController;
import controller.MedicamentoController;
import controller.ExameController;

/**
 * Classe para a visualização e interação com Prescrições.
 
 */
public class PrescricaoView extends BaseView {
    private final PrescricaoController controller;
    private final MedicamentoController medicamentoController;
    private final ExameController exameController;
    private static final String ENTIDADE = "Prescrição";

    public PrescricaoView(PrescricaoController controller,
                          MedicamentoController medicamentoController,
                          ExameController exameController) {
        this.controller = controller;
        this.medicamentoController = medicamentoController;
        this.exameController = exameController;
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
        ArrayList<Medicamento> medicamentos = selecionarMedicamentos();
        if (medicamentos == null) return;

        ArrayList<Exame> exames = selecionarExames();
        if (exames == null) return;

        try {
            controller.create(medicamentos, exames);
            showSuccess(ENTIDADE + " " + SUCESSO_ADICIONAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void atualizar() {
        Integer id = readInt("ID da " + ENTIDADE + " a ser atualizada:");
        if (id == null) return;

        Prescricao prescricao = controller.read(id);
        if (prescricao == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        if (showConfirmation("Deseja atualizar os medicamentos?")) {
            ArrayList<Medicamento> medicamentos = selecionarMedicamentos();
            if (medicamentos != null) {
                prescricao.setMedicamentosPrescritos(medicamentos);
            }
        }

        if (showConfirmation("Deseja atualizar os exames?")) {
            ArrayList<Exame> exames = selecionarExames();
            if (exames != null) {
                prescricao.setExamesPrescritos(exames);
            }
        }

        try {
            controller.update(prescricao);
            showSuccess(ENTIDADE + " " + SUCESSO_ATUALIZAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void remover() {
        Integer id = readInt("ID da " + ENTIDADE + " a ser removida:");
        if (id == null) return;

        Prescricao prescricao = controller.read(id);
        if (prescricao == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        if (!showConfirmation(CONFIRMAR_REMOCAO)) return;

        try {
            controller.delete(prescricao);
            showSuccess(ENTIDADE + " " + SUCESSO_REMOVER);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void buscar() {
        Integer id = readInt("ID da " + ENTIDADE + ":");
        if (id == null) return;

        Prescricao prescricao = controller.read(id);
        if (prescricao == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        showMessage(formatarPrescricao(prescricao));
    }

    private void listar() {
        List<Prescricao> prescricoes = controller.listAll();
        if (prescricoes.isEmpty()) {
            showMessage("Nenhuma " + ENTIDADE + " cadastrada.");
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de " + ENTIDADE + "s:\n\n");
        prescricoes.forEach(prescricao -> sb.append(formatarPrescricao(prescricao)).append("\n"));
        showMessage(sb.toString());
    }

    private ArrayList<Medicamento> selecionarMedicamentos() {
        ArrayList<Medicamento> medicamentos = new ArrayList<>();
        while (true) {
            Integer id = readInt("ID do Medicamento (0 para finalizar):");
            if (id == null || id == 0) break;

            Medicamento medicamento = medicamentoController.read(id);
            if (medicamento == null) {
                showError("Medicamento não encontrado!");
                continue;
            }
            medicamentos.add(medicamento);
            showSuccess("Medicamento adicionado à prescrição!");
        }
        return medicamentos.isEmpty() ? null : medicamentos;
    }

    private ArrayList<Exame> selecionarExames() {
        ArrayList<Exame> exames = new ArrayList<>();
        while (true) {
            Integer id = readInt("ID do Exame (0 para finalizar):");
            if (id == null || id == 0) break;

            Exame exame = exameController.read(id);
            if (exame == null) {
                showError("Exame não encontrado!");
                continue;
            }
            exames.add(exame);
            showSuccess("Exame adicionado à prescrição!");
        }
        return exames.isEmpty() ? null : exames;
    }

    private String formatarPrescricao(Prescricao prescricao) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ID: %d\n\n", prescricao.getIdPrescricao()));

        sb.append("Medicamentos Prescritos:\n");
        if (prescricao.getMedicamentosPrescritos().isEmpty()) {
            sb.append("- Nenhum medicamento prescrito\n");
        } else {
            prescricao.getMedicamentosPrescritos().forEach(med ->
                    sb.append(String.format("- %s (ID: %d)\n",
                            med.getNome(), med.getIdMedicamento()))
            );
        }

        sb.append("\nExames Prescritos:\n");
        if (prescricao.getExamesPrescritos().isEmpty()) {
            sb.append("- Nenhum exame prescrito\n");
        } else {
            prescricao.getExamesPrescritos().forEach(exame ->
                    sb.append(String.format("- %s (ID: %d)\n",
                            exame.getTipoDoExame(), exame.getIdExame()))
            );
        }

        return sb.toString();
    }
}