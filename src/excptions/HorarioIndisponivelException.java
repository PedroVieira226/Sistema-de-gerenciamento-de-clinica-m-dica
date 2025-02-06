package excptions;

public class HorarioIndisponivelException extends RuntimeException {
    public HorarioIndisponivelException(String message) {
        super(message);
    }
}
