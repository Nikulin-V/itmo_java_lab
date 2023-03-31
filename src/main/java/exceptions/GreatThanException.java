package exceptions;

public class GreatThanException extends WarningException {
    private final long bound;

    public GreatThanException(long bound) {
        this.bound = bound;
    }

    @Override
    public String getMessage() {
        return "Введённое значение должно не быть больше " + bound;
    }
}
