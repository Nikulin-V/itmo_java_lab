package exceptions;

public class NotGreatThanException extends WarningException {
    private final long bound;

    public NotGreatThanException(long bound) {
        this.bound = bound;
    }

    @Override
    public String getMessage() {
        return "Введённое значение должно быть больше " + bound;
    }
}
