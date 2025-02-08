package view;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe base para views, contendo métodos comuns de interação com o usuário via JOptionPane.
 */
public abstract class BaseView {
    protected SimpleDateFormat dateFormat;

    public BaseView() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    protected String readString(String prompt) {
        return JOptionPane.showInputDialog(null, prompt);
    }

    protected int readInt(String prompt) {
        String input = JOptionPane.showInputDialog(null, prompt);
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Valor inválido! Insira um número inteiro.");
            return -1; // Ou lançar uma exceção personalizada
        }
    }

    protected Date readDate(String prompt) {
        while (true) {
            String dateStr = JOptionPane.showInputDialog(null, prompt + " (dd/MM/yyyy)");
            try {
                return dateFormat.parse(dateStr);
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(null, "Formato de data inválido! Use dd/MM/yyyy.");
            }
        }
    }
}