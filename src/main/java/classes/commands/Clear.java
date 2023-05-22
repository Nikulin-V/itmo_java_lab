package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.console.TextColor;
import classes.sql_managers.SQLManager;
import interfaces.Commandable;

public class Clear extends NamedCommand implements Commandable {
    @Override
    public Response execute(Object inputData, String userID) {
        int countDeleted = SQLManager.executeUpdate("DELETE FROM movies WHERE user_id="+userID);
        return new Response(0).setData(TextColor.cyan("Удалено ваших элементов коллекции: "+countDeleted));
    }

    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tочистить коллекцию";
    }

}

