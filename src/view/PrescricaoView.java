package view;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import entities.*;
import controller.PrescricaoController;
import controller.MedicamentoController;
import controller.ExameController;

/**
 * Classe responsável pela interface gráfica de gerenciamento de prescrições médicas.
 * Permite a criação, atualização, remoção, busca e listagem de prescrições.
 *
 * @author CaioSoandrd
 * @version 1.0
 * @since 2025-02-09 20:19:21
 */
public class PrescricaoView extends BaseView {
    private final PrescricaoController controller;
    private final MedicamentoController medicamentoController;
    private final ExameController exameController;
    private static final String ENTIDADE = "Prescrição";

    /**
     * Construtor da classe PrescricaoView.
     * Inicializa os controllers necessários e exibe o menu principal.
     *
     * @param controller O controller de prescrições
     * @param medicamentoController O controller de medicamentos
     * @param exameController O controller de exames
     */
    public PrescricaoView(PrescricaoController controller,
                          MedicamentoController medicamentoController,
                          ExameController exameController) {
        this.controller = controller;
        this.medicamentoController = medicamentoController;
        this.exameController = exameController;
        showMenu();
    }

    /**
     * Exibe o menu principal de opções para gerenciamento de prescrições.
     */
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

    /**
     * Adiciona uma nova prescrição ao sistema.
     */
    private void adicionar() {
        ArrayList<Integer> medicamentosIds = selecionarMedicamentosIds();
        ArrayList<Integer> examesIds = selecionarExamesIds();

        if ((medicamentosIds == null || medicamentosIds.isEmpty()) &&
                (examesIds == null || examesIds.isEmpty())) {
            showError("É necessário selecionar pelo menos um medicamento ou exame!");
            return;
        }

        try {
            controller.createFromIds(
                    medicamentosIds != null ? medicamentosIds : new ArrayList<>(),
                    examesIds != null ? examesIds : new ArrayList<>()
            );
            showSuccess(ENTIDADE + " " + SUCESSO_ADICIONAR);
        } catch (Exception e) {
            showError("Erro ao criar prescrição: " + e.getMessage());
        }
    }

    /**
     * Atualiza uma prescrição existente criando uma nova e removendo a antiga.
     */
    private void atualizar() {
        Integer id = readInt("ID da " + ENTIDADE + ":");
        if (id == null) return;

        Prescricao prescricao = controller.read(id);
        if (prescricao == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        ArrayList<Integer> medicamentosIds = prescricao.getMedicamentosPrescritos().stream()
                .map(Medicamento::getIdMedicamento)
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> examesIds = prescricao.getExamesPrescritos().stream()
                .map(Exame::getIdExame)
                .collect(Collectors.toCollection(ArrayList::new));

        boolean atualizou = false;

        if (showConfirmation("Deseja atualizar os medicamentos?")) {
            ArrayList<Integer> novosMedicamentos = selecionarMedicamentosIds();
            if (novosMedicamentos != null) {
                medicamentosIds = novosMedicamentos;
                atualizou = true;
            }
        }

        if (showConfirmation("Deseja atualizar os exames?")) {
            ArrayList<Integer> novosExames = selecionarExamesIds();
            if (novosExames != null) {
                examesIds = novosExames;
                atualizou = true;
            }
        }

        if (!atualizou) {
            showMessage("Nenhuma alteração realizada.");
            return;
        }

        try {
            // Cria nova prescrição com os dados atualizados
            Prescricao novaPrescricao = controller.createFromIds(medicamentosIds, examesIds);
            if (novaPrescricao != null) {
                // Remove a prescrição antiga
                controller.delete(id);
                showSuccess(ENTIDADE + " " + SUCESSO_ATUALIZAR);
            }
        } catch (Exception e) {
            showError("Erro ao atualizar prescrição: " + e.getMessage());
        }
    }

    /**
     * Remove uma prescrição do sistema.
     */
    private void remover() {
        Integer id = readInt("ID da " + ENTIDADE + ":");
        if (id == null) return;

        Prescricao prescricao = controller.read(id);
        if (prescricao == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        if (!showConfirmation(CONFIRMAR_REMOCAO)) return;

        try {
            controller.delete(id);
            showSuccess(ENTIDADE + " " + SUCESSO_REMOVER);
        } catch (Exception e) {
            showError("Erro ao remover prescrição: " + e.getMessage());
        }
    }

    /**
     * Busca e exibe uma prescrição específica.
     */
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

    /**
     * Lista todas as prescrições cadastradas.
     */
    private void listar() {
        List<Prescricao> prescricoes = controller.listAll();
        if (prescricoes.isEmpty()) {
            showMessage("Nenhuma " + ENTIDADE + " cadastrada.");
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de " + ENTIDADE + "s:\n\n");
        for (Prescricao prescricao : prescricoes) {
            sb.append(formatarPrescricao(prescricao)).append("\n-------------------\n");
        }
        showMessage(sb.toString());
    }

    /**
     * Permite a seleção de medicamentos por seus IDs.
     *
     * @return ArrayList contendo os IDs dos medicamentos selecionados,
     *         ou null se nenhum medicamento for selecionado
     */
    private ArrayList<Integer> selecionarMedicamentosIds() {
        ArrayList<Integer> medicamentosIds = new ArrayList<>();
        List<Medicamento> medicamentos = medicamentoController.listAll();

        if (medicamentos.isEmpty()) {
            showMessage("Não há medicamentos cadastrados no sistema.");
            return null;
        }

        listarMedicamentosDisponiveis(medicamentos);

        while (true) {
            Integer id = readInt("ID do Medicamento (0 para finalizar):");
            if (id == null || id == 0) break;

            if (medicamentoController.read(id) != null) {
                medicamentosIds.add(id);
                showSuccess("Medicamento adicionado à prescrição!");
            } else {
                showError("Medicamento não encontrado!");
            }
        }

        return medicamentosIds.isEmpty() ? null : medicamentosIds;
    }

    /**
     * Permite a seleção de exames por seus IDs.
     *
     * @return ArrayList contendo os IDs dos exames selecionados,
     *         ou null se nenhum exame for selecionado
     */
    private ArrayList<Integer> selecionarExamesIds() {
        ArrayList<Integer> examesIds = new ArrayList<>();
        List<Exame> exames = exameController.listAll();

        if (exames.isEmpty()) {
            showMessage("Não há exames cadastrados no sistema.");
            return null;
        }

        listarExamesDisponiveis(exames);

        while (true) {
            Integer id = readInt("ID do Exame (0 para finalizar):");
            if (id == null || id == 0) break;

            if (exameController.read(id) != null) {
                examesIds.add(id);
                showSuccess("Exame adicionado à prescrição!");
            } else {
                showError("Exame não encontrado!");
            }
        }

        return examesIds.isEmpty() ? null : examesIds;
    }

    /**
     * Lista os medicamentos disponíveis no sistema.
     *
     * @param medicamentos Lista de medicamentos a ser exibida
     */
    private void listarMedicamentosDisponiveis(List<Medicamento> medicamentos) {
        StringBuilder sb = new StringBuilder("Medicamentos Disponíveis:\n\n");
        for (Medicamento med : medicamentos) {
            sb.append(String.format("ID: %d - %s\n",
                    med.getIdMedicamento(), med.getNome()));
        }
        showMessage(sb.toString());
    }

    /**
     * Lista os exames disponíveis no sistema.
     *
     * @param exames Lista de exames a ser exibida
     */
    private void listarExamesDisponiveis(List<Exame> exames) {
        StringBuilder sb = new StringBuilder("Exames Disponíveis:\n\n");
        for (Exame exame : exames) {
            sb.append(String.format("ID: %d - %s\n",
                    exame.getIdExame(), exame.getTipoDoExame()));
        }
        showMessage(sb.toString());
    }

    /**
     * Formata os dados de uma prescrição para exibição.
     *
     * @param prescricao A prescrição a ser formatada
     * @return String contendo os dados formatados da prescrição
     */
    private String formatarPrescricao(Prescricao prescricao) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ID: %d\n\n", prescricao.getIdPrescricao()));

        sb.append("Medicamentos Prescritos:\n");
        List<Medicamento> medicamentos = prescricao.getMedicamentosPrescritos();
        if (medicamentos.isEmpty()) {
            sb.append("- Nenhum medicamento prescrito\n");
        } else {
            for (Medicamento med : medicamentos) {
                sb.append(String.format("- %s (ID: %d)\n",
                        med.getNome(), med.getIdMedicamento()));
            }
        }

        sb.append("\nExames Prescritos:\n");
        List<Exame> exames = prescricao.getExamesPrescritos();
        if (exames.isEmpty()) {
            sb.append("- Nenhum exame prescrito\n");
        } else {
            for (Exame exame : exames) {
                sb.append(String.format("- %s (ID: %d)\n",
                        exame.getTipoDoExame(), exame.getIdExame()));
            }
        }

        return sb.toString();
    }
}