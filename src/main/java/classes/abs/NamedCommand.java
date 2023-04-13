package classes.abs;

import classes.console.CommandHandler;

public abstract class NamedCommand {
    public String getName() {
        return CommandHandler.camelToSnake(this.getClass().getSimpleName());
    }
}
