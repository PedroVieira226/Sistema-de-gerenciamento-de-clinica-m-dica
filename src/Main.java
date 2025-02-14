import javax.swing.*;
import view.*;
import controller.*;

/**
 * Classe principal que inicializa o sistema.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Inicialização dos Controllers
                MedicoController medicoController = new MedicoController();
                PacienteController pacienteController = new PacienteController();
                ConsultaController consultaController = new ConsultaController();
                ExameController exameController = new ExameController();
                MedicamentoController medicamentoController = new MedicamentoController();
                PrescricaoController prescricaoController = new PrescricaoController();

                // Verificação se os controllers foram inicializados corretamente
                if (medicoController == null || pacienteController == null || consultaController == null ||
                        exameController == null || medicamentoController == null || prescricaoController == null) {
                    throw new Exception("Erro na inicialização dos controllers.");
                }

                while (true) {
                    String[] opcoes = {
                            "Gerenciar Médicos",
                            "Gerenciar Pacientes",
                            "Gerenciar Consultas",
                            "Gerenciar Exames",
                            "Gerenciar Medicamentos",
                            "Gerenciar Prescrições",
                            "Sair"
                    };

                    int escolha = JOptionPane.showOptionDialog(
                            null,
                            "Selecione um módulo:",
                            "Sistema de Gerenciamento de Clínica Médica",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            opcoes,
                            opcoes[0]
                    );

                    switch (escolha) {
                        case 0 -> new MedicoView(medicoController);
                        case 1 -> new PacienteView(pacienteController, consultaController);
                        case 2 -> new ConsultaView(consultaController, medicoController,
                                pacienteController, prescricaoController);
                        case 3 -> new ExameView(exameController);
                        case 4 -> new MedicamentoView(medicamentoController);
                        case 5 -> new PrescricaoView(prescricaoController,
                                medicamentoController,
                                exameController);
                        case -1, 6 -> {
                            if (JOptionPane.showConfirmDialog(null,
                                    "Deseja realmente sair do sistema?",
                                    "Confirmação",
                                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                                System.exit(0);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Erro ao inicializar o sistema: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}