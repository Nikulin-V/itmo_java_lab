package exceptions;

import java.util.UUID;

public class NotUniqueException extends WarningException {
    private final UUID id;

    public NotUniqueException(UUID id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Введённое значение должно быть уникальным (уже используется в фильме с ID: " + id + ")";
    }
}
