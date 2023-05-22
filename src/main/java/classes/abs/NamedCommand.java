package classes.abs;

import classes.Response;
import classes.console.CommandHandler;

import java.lang.reflect.InvocationTargetException;

public abstract class NamedCommand {
    public boolean isNeedInput() {
        return false;
    }

    public Response execute(Object inputData, String userID) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return null;
    }

    public boolean hasTransferData() {
        return false;
    }

    public String getName() {
        return CommandHandler.camelToSnake(this.getClass().getSimpleName());
    }
}
