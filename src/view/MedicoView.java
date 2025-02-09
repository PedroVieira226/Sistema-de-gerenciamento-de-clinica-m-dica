package view;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import entities.Medico;
import controller.MedicoController;

/**
 * Classe para a visualização e interação com Médicos.
 * @author CaioSoandrd
 * @version 1.0
 * @since 2025-02-09
 */
public class MedicoView extends BaseView {
    private final MedicoController controller;
    private static final String ENTIDADE = "Médico";

    public MedicoView(MedicoController controller) {
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

        String cpf = readString("CPF do " + ENTIDADE + ":");
        if (cpf == null || !validarCPF(cpf)) return;

        Date dataNascimento = readDate("Data de Nascimento do " + ENTIDADE);
        if (dataNascimento == null) return;

        String crm = readString("CRM do " + ENTIDADE + ":");
        if (crm == null || crm.isEmpty()) {
            showError("CRM é obrigatório!");
            return;
        }

        String especialidade = readString("Especialidade do " + ENTIDADE + ":");
        if (especialidade == null || especialidade.isEmpty()) {
            showError("Especialidade é obrigatória!");
            return;
        }

        try {
            controller.create(nome, cpf, dataNascimento, crm, especialidade);
            showSuccess(ENTIDADE + " " + SUCESSO_ADICIONAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void atualizar() {
        String cpf = readString("CPF do " + ENTIDADE + " a ser atualizado:");
        if (cpf == null || !validarCPF(cpf)) return;

        Medico medico = controller.read(cpf);
        if (medico == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        String nome = readString("Novo Nome do " + ENTIDADE + ":");
        if (nome != null && !nome.isEmpty()) medico.setNome(nome);

        Date dataNascimento = readDate("Nova Data de Nascimento do " + ENTIDADE);
        if (dataNascimento != null) medico.setDataDeNascimento(dataNascimento);

        String crm = readString("Novo CRM do " + ENTIDADE + ":");
        if (crm != null && !crm.isEmpty()) medico.setCrm(crm);

        String especialidade = readString("Nova Especialidade do " + ENTIDADE + ":");
        if (especialidade != null && !especialidade.isEmpty()) medico.setEspecialidade(especialidade);

        try {
            controller.update(medico);
            showSuccess(ENTIDADE + " " + SUCESSO_ATUALIZAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void remover() {
        String cpf = readString("CPF do " + ENTIDADE + " a ser removido:");
        if (cpf == null || !validarCPF(cpf)) return;

        Medico medico = controller.read(cpf);
        if (medico == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        if (!showConfirmation(CONFIRMAR_REMOCAO)) return;

        try {
            controller.delete(medico);
            showSuccess(ENTIDADE + " " + SUCESSO_REMOVER);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void buscar() {
        String cpf = readString("CPF do " + ENTIDADE + ":");
        if (cpf == null || !validarCPF(cpf)) return;

        Medico medico = controller.read(cpf);
        if (medico == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        showMessage(formatarMedico(medico));
    }

    private void listar() {
        List<Medico> medicos = controller.listAll();
        if (medicos.isEmpty()) {
            showMessage("Nenhum " + ENTIDADE + " cadastrado.");
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de " + ENTIDADE + "s:\n\n");
        medicos.forEach(medico -> sb.append(formatarMedico(medico)).append("\n"));
        showMessage(sb.toString());
    }

    private String formatarMedico(Medico medico) {
        return String.format("""
            CPF: %s
            Nome: %s
            Data Nascimento: %s
            CRM: %s
            Especialidade: %s""",
                medico.getCpf(),
                medico.getNome(),
                formatDate(medico.getDataDeNascimento()),
                medico.getCrm(),
                medico.getEspecialidade());
    }

    private boolean validarCPF(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            showError("CPF deve conter 11 dígitos!");
            return false;
        }
        return true;
    }
}