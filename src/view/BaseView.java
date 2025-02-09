package view;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe base para views, contendo métodos comuns de interação com o usuário via JOptionPane.
 */
public abstract class BaseView {
    protected final SimpleDateFormat dateFormat;
    protected static final String ERRO_NAO_ENCONTRADO = "não encontrado!";
    protected static final String ERRO_ENTRADA_INVALIDA = "Entrada inválida!";
    protected static final String SUCESSO_ADICIONAR = "adicionado com sucesso!";
    protected static final String SUCESSO_ATUALIZAR = "atualizado com sucesso!";
    protected static final String SUCESSO_REMOVER = "removido com sucesso!";
    protected static final String CONFIRMAR_REMOCAO = "Confirma a remoção?";

    public BaseView() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    protected String readString(String prompt) {
        String input = JOptionPane.showInputDialog(null, prompt);
        return input != null ? input.trim() : null;
    }

    protected Integer readInt(String prompt) {
        try {
            String input = readString(prompt);
            if (input == null || input.isEmpty()) return null;
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            showError("Por favor, insira um número inteiro válido.");
            return null;
        }
    }

    protected Double readDouble(String prompt) {
        try {
            String input = readString(prompt);
            if (input == null || input.isEmpty()) return null;
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            showError("Por favor, insira um número válido.");
            return null;
        }
    }

    protected Date readDate(String prompt) {
        while (true) {
            String input = readString(prompt + " (dd/MM/yyyy)");
            if (input == null) return null;
            try {
                return dateFormat.parse(input);
            } catch (ParseException e) {
                showError("Formato de data inválido! Use dd/MM/yyyy.");
            }
        }
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(null, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    protected void showSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    protected boolean showConfirmation(String message) {
        return JOptionPane.showConfirmDialog(null, message, "Confirmação",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    protected String formatDate(Date date) {
        return date != null ? dateFormat.format(date) : "";
    }
}