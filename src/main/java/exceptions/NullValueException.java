package exceptions;

public class NullValueException extends DangerException {
    @Override
    public String getMessage() {
        return "Это обязательное поле";
    }
}
