package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;

public class Show extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tвывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        CollectionManager collectionManager = new CollectionManager();
        ArrayList<Movie> movies = collectionManager.getCollection();
        StringBuilder output = new StringBuilder(TextColor.cyan("Содержимое коллекции:\n"));
        if (movies.size() != 0)
            for (Movie movie : movies)
                output.append("\t").append(movie.toString());
        else output.append(TextColor.cyan("\tПусто"));
        return new Response(0, output.toString());
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
