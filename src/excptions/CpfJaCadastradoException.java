package excptions;

/**
 * Exceção lançada quando um CPF já está cadastrado no sistema.
 * Essa exceção é usada para evitar a duplicação de cadastros de pacientes ou médicos com o mesmo CPF.
 */

public class CpfJaCadastradoException extends RuntimeException {
    /**
     * Cria uma nova exceção com uma mensagem de erro específica.
     *
     * @param message A mensagem de erro que descreve a situação.
     */

    public CpfJaCadastradoException(String message) {
        super(message);
    }
}