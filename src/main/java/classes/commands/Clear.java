package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import interfaces.Commandable;

public class Clear extends NamedCommand implements Commandable {
    @Override
    public String execute(Object inputData) {
        new CollectionManager().clear();
        System.out.println(TextColor.cyan("Коллекция был очищена"));
        return "Выполнено";
    }

    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tочистить коллекцию";
    }
    @Override
    public boolean isNeedInput() {
        return false;
    }
    @Override
    public boolean hasTransferData() {
        return false;
    }
}

