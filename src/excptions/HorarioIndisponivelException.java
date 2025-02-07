package excptions;
/**
 * Exceção lançada quando um horário não está disponível para agendamento de consulta.
 * Essa exceção é usada para evitar que dois agendamentos ocorram no mesmo horário com o mesmo médico.
 */
public class HorarioIndisponivelException extends RuntimeException {
    /**
     * Cria uma nova exceção com uma mensagem de erro específica.
     *
     * @param message A mensagem de erro que descreve a situação.
     */
    public HorarioIndisponivelException(String message) {
        super(message);
    }
}
