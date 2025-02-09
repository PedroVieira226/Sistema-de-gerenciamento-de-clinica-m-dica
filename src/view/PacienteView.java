package view;

import javax.swing.*;
import java.util.Date;
import java.util.List;
import entities.Paciente;
import entities.Consulta;
import controller.PacienteController;
import controller.ConsultaController;
import excptions.CpfJaCadastradoException;
import java.util.ArrayList;

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
            6. Histórico de Consultas
            7. Sair""", ENTIDADE, ENTIDADE, ENTIDADE, ENTIDADE, ENTIDADE);

        while (true) {
            String option = readString(menu);
            if (option == null) return;

            switch (option) {
                case "1" -> adicionar();
                case "2" -> atualizar();
                case "3" -> remover();
                case "4" -> buscar();
                case "5" -> listar();
                case "6" -> historicoConsultas();
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
        if (cpf == null || cpf.length() != 11) {
            showError("CPF deve conter 11 dígitos!");
            return;
        }

        Date dataNascimento = readDate("Data de Nascimento");
        if (dataNascimento == null) return;

        try {
            controller.create(nome, cpf, dataNascimento);
            showSuccess(ENTIDADE + " " + SUCESSO_ADICIONAR);
        } catch (CpfJaCadastradoException e) {
            showError("CPF já cadastrado!");
        } catch (Exception e) {
            showError("Erro ao adicionar " + ENTIDADE + ": " + e.getMessage());
        }
    }

    private void atualizar() {
        String cpf = readString("CPF do " + ENTIDADE + " a ser atualizado:");
        if (cpf == null || cpf.length() != 11) {
            showError("CPF inválido!");
            return;
        }

        Paciente paciente = controller.read(cpf);
        if (paciente == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        String nome = readString("Novo Nome do " + ENTIDADE + " (vazio para manter):");
        if (nome != null && !nome.isEmpty()) {
            paciente.setNome(nome);
        }

        Date dataNascimento = readDate("Nova Data de Nascimento (vazio para manter)");
        if (dataNascimento != null) {
            paciente.setDataDeNascimento(dataNascimento);
        }

        try {
            controller.update(paciente);
            showSuccess(ENTIDADE + " " + SUCESSO_ATUALIZAR);
        } catch (Exception e) {
            showError("Erro ao atualizar " + ENTIDADE + ": " + e.getMessage());
        }
    }

    private void remover() {
        String cpf = readString("CPF do " + ENTIDADE + " a ser removido:");
        if (cpf == null || cpf.length() != 11) {
            showError("CPF inválido!");
            return;
        }

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
            showError("Erro ao remover " + ENTIDADE + ": " + e.getMessage());
        }
    }

    private void buscar() {
        String cpf = readString("CPF do " + ENTIDADE + ":");
        if (cpf == null || cpf.length() != 11) {
            showError("CPF inválido!");
            return;
        }

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
            showMessage("Nenhum " + ENTIDADE + " cadastrado.");
            return;
        }

        StringBuilder sb = new StringBuilder("Lista de " + ENTIDADE + "s:\n\n");
        for (Paciente paciente : pacientes) {
            sb.append(formatarPaciente(paciente)).append("\n-------------------\n");
        }
        showMessage(sb.toString());
    }

    private void historicoConsultas() {
        String cpf = readString("CPF do " + ENTIDADE + ":");
        if (cpf == null || cpf.length() != 11) {
            showError("CPF inválido!");
            return;
        }

        Paciente paciente = controller.read(cpf);
        if (paciente == null) {
            showError(ENTIDADE + " " + ERRO_NAO_ENCONTRADO);
            return;
        }

        List<Consulta> todasConsultas = consultaController.listAll();
        List<Consulta> consultasPaciente = new ArrayList<>();

        for (Consulta consulta : todasConsultas) {
            if (consulta.getPacienteDaConsulta().getCpf().equals(cpf)) {
                consultasPaciente.add(consulta);
            }
        }

        if (consultasPaciente.isEmpty()) {
            showMessage("Nenhuma consulta encontrada para este paciente.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Histórico de Consultas ===\n");
        sb.append(formatarPaciente(paciente)).append("\n\n");
        sb.append("Consultas:\n");

        for (Consulta consulta : consultasPaciente) {
            sb.append(formatarConsulta(consulta)).append("\n-------------------\n");
        }

        showMessage(sb.toString());
    }

    private String formatarPaciente(Paciente paciente) {
        return String.format("""
            CPF: %s
            Nome: %s
            Data de Nascimento: %s
            ID: %d""",
                paciente.getCpf(),
                paciente.getNome(),
                formatDate(paciente.getDataDeNascimento()),
                paciente.getIdPaciente());
    }

    private String formatarConsulta(Consulta consulta) {
        return String.format("""
            Data: %s
            Médico: %s
            Especialidade: %s
            Tipo: %s
            Status: %s""",
                formatDate(consulta.getDataDaConsulta()),
                consulta.getMedicoDaConsulta().getNome(),
                consulta.getMedicoDaConsulta().getEspecialidade(),
                consulta.getTipoConsulta(),
                consulta.getStatusConsultaDaConsulta());
    }
}