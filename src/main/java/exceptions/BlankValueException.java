package exceptions;

public class BlankValueException extends WarningException {
    @Override
    public String getMessage() {
        return "Введённое значение не может быть пустой строкой";
    }
}
