package classes.abs;

import classes.Response;
import classes.console.CommandHandler;

public abstract class NamedCommand {
    public boolean isNeedInput() {
        return false;
    }

    public Response execute(Object inputData) {
        return null;
    }

    public boolean hasTransferData() {
        return false;
    }

    public String getName() {
        return CommandHandler.camelToSnake(this.getClass().getSimpleName());
    }
}
