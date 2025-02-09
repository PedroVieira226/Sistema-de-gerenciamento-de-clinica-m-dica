package view;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import entities.*;
import controller.PacienteController;
import controller.ConsultaController;
import excptions.CpfJaCadastradoException;

/**
 * Classe para a visualização e interação com Pacientes.
 */
public class PacienteView extends BaseView {
    private final PacienteController controller;
    private final ConsultaController consultaController;
    private static final String ENTIDADE = "Paciente";

    public PacienteView(PacienteController controller, ConsultaController consultaController) {
        this.controller = controller;
        this.consultaController = consultaController;
        showMenu();
    }

    public void showMenu() {
        String menu = String.format("""
            1. Adicionar %s
            2. Atualizar %s
            3. Remover %s
            4. Buscar %s
            5. Listar %ss
            6. Detalhes do %s
            7. Sair""", ENTIDADE, ENTIDADE, ENTIDADE, ENTIDADE, ENTIDADE, ENTIDADE);

        while (true) {
            String option = readString(menu);
            if (option == null) return;

            switch (option) {
                case "1" -> adicionar();
                case "2" -> atualizar();
                case "3" -> remover();
                case "4" -> buscar();
                case "5" -> listar();
                case "6" -> detalhes();
                case "7" -> { return; }
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

        Date dataNascimento = readDate("Data de Nascimento");
        if (dataNascimento == null) return;

        try {
            controller.create(nome, cpf, dataNascimento);
            showSuccess(ENTIDADE + " " + SUCESSO_ADICIONAR);
        } catch (CpfJaCadastradoException e) {
            showError(e.getMessage());
        }
    }

    private void atualizar() {
        String cpf = readString("CPF do " + ENTIDADE + " a ser atualizado:");
        if (cpf == null || !validarCPF(cpf)) return;

        Paciente paciente = controller.read(cpf);
        if (paciente == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        String nome = readString("Novo Nome do " + ENTIDADE + ":");
        if (nome != null && !nome.isEmpty()) paciente.setNome(nome);

        Date dataNascimento = readDate("Nova Data de Nascimento");
        if (dataNascimento != null) paciente.setDataDeNascimento(dataNascimento);

        try {
            controller.update(paciente);
            showSuccess(ENTIDADE + " " + SUCESSO_ATUALIZAR);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void remover() {
        String cpf = readString("CPF do " + ENTIDADE + " a ser removido:");
        if (cpf == null || !validarCPF(cpf)) return;

        Paciente paciente = controller.read(cpf);
        if (paciente == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        if (!showConfirmation(CONFIRMAR_REMOCAO)) return;

        try {
            controller.delete(paciente);
            showSuccess(ENTIDADE + " " + SUCESSO_REMOVER);
        } catch (Exception e) {
            showError(e.getMessage());
        }
    }

    private void buscar() {
        String cpf = readString("CPF do " + ENTIDADE + ":");
        if (cpf == null || !validarCPF(cpf)) return;

        Paciente paciente = controller.read(cpf);
        if (paciente == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        showMessage(formatarPaciente(paciente));
    }

    private void listar() {
        List<Paciente> pacientes = controller.listAll();
        if (pacientes.isEmpty()) {
            showMessage("Nenhum " + ENTIDADE + " cadastra