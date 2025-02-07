package excptions;
/**
 * Exceção lançada quando um paciente já possui uma consulta no mesmo dia.
 */
public class PacientePossuiConsultaNoMesmoDIa extends RuntimeException {
    /**
     * Cria uma nova exceção com a mensagem especificada.
     *
     * @param message A mensagem de erro.
     */
    public PacientePossuiConsultaNoMesmoDIa(String message) {
        super(message);
    }
}
