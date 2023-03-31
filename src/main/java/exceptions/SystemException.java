package exceptions;

public class SystemException extends DangerException {
    @Override
    public String getMessage() {
        return "Системная ошибка";
    }
}
