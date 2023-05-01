package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import interfaces.Commandable;

public class Clear extends NamedCommand implements Commandable {
    @Override
    public Response execute(Object inputData) {
        new CollectionManager().clear();
        return new Response(0).setData(TextColor.cyan("Коллекция был очищена"));
    }

    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tочистить коллекцию";
    }

}

