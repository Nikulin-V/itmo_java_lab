package classes;

import classes.console.CommandHandler;

public abstract class NamedCommand {
    public String getName() {
        return CommandHandler.camelToSnake(this.getClass().getSimpleName());
    }
}
