package classes.commands;


import classes.Response;
import classes.abs.NamedCommand;
import interfaces.Commandable;

public class ClearDataBase extends NamedCommand implements Commandable {
    @Override
    public Response execute(Object inputData) {
        return null;
    }

    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t\t\t\t\t-\tочистить файл коллекции";
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

