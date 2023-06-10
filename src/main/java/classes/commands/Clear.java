package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.sql_managers.SQLManager;
import interfaces.Commandable;

import java.util.List;

public class Clear extends NamedCommand implements Commandable {
    @Override
    public Response execute(Object inputData, String userID) {
        int countDeleted = SQLManager.executeDelete(userID);

        List<Movie> movies = new CollectionManager().getCollection();
        if (countDeleted > 0) movies.removeIf(movie -> userID.equals(movie.getUserID()));
        return new Response(0, TextColor.cyan("Удалено количество: " + countDeleted));
    }

    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tочистить коллекцию";
    }
}

