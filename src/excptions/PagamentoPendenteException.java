package excptions;
/**
 * Exceção lançada quando há um pagamento pendente relacionado a uma consulta ou serviço.
 * Essa exceção é usada para garantir que pagamentos sejam resolvidos antes de prosseguir com operações críticas.
 */
public class PagamentoPendenteException extends RuntimeException {
    /**
     * Cria uma nova exceção com uma mensagem de erro específica.
     *
     * @param message A mensagem de erro que descreve a situação.
     */
    public PagamentoPendenteException(String message) {
        super(message);
    }
}
