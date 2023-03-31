package exceptions;

public class NoSuchCommandException extends DangerException {
    @Override
    public String getMessage() {
        return "Команда не существует. Используйте help для справки";
    }
}
