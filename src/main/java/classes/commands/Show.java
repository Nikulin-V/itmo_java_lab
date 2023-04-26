package classes.commands;

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
    public String execute(Object inputData) {
        CollectionManager collectionManager = new CollectionManager();
        ArrayList<Movie> movies = collectionManager.getCollection();
        String output = TextColor.cyan("Содержимое коллекции:\n");
        if (movies.size() != 0)
            for (Movie movie : movies)
                output += "\t" + movie.toString();
        else output += TextColor.cyan("\tПусто");
        return output;
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
