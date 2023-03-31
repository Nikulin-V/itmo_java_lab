package exceptions;

public class BadValueLengthException extends WarningException {
    private final int length;

    public BadValueLengthException(int length) {
        this.length = length;
    }

    @Override
    public String getMessage() {
        return "Длина должна быть не меньше " + length;
    }
}
