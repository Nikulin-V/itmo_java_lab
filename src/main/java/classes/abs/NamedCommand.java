package classes.abs;

import classes.console.CommandHandler;

public abstract class NamedCommand {
    public boolean isNeedInput() {
        return false;
    }

    public Object execute(Object inputData) {
        return null;
    }

    public boolean hasTransferData() {
        return false;
    }

    public String getName() {
        return CommandHandler.camelToSnake(this.getClass().getSimpleName());
    }
}
