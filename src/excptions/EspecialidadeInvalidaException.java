package excptions;
/**
 * Exceção lançada quando uma especialidade inválida é fornecida para um médico ou consulta.
 * Essa exceção é usada para garantir que médicos só realizem consultas dentro de suas especialidades.
 */

public class EspecialidadeInvalidaException extends RuntimeException {
    /**
     * Cria uma nova exceção com uma mensagem de erro específica.
     *
     * @param message A mensagem de erro que descreve a situação.
     */
    public EspecialidadeInvalidaException(String message) {
        super(message);
    }
}
