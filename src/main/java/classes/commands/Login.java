package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.console.TextColor;
import interfaces.Commandable;

public class Login extends NamedCommand implements Commandable {
    @Override
    public Response execute(Object inputData, String userID) {
        return new Response(0, TextColor.cyan("Коллекция был очищена"));
    }

    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tочистить коллекцию";
    }

}

