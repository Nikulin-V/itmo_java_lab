package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;

public class PrintDescending extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t-\tвывести элементы коллекции в порядке убывания";
    }

    @Override
    public String execute(Object inputData) {
        CollectionManager collectionManager = new CollectionManager();
        ArrayList<Movie> collection = new ArrayList<>(collectionManager.getCollection());
        CollectionManager.sort(collection);
        StringBuilder output = new StringBuilder(TextColor.cyan("Содержимое коллекции в порядке убывания: \n"));
        if (collection.size() != 0)
            for (Movie movie : collection)
                output.append("\t").append(movie.toString()).append("\n");
        else output = new StringBuilder(TextColor.cyan("\tПусто"));
        return output.toString();
    }
}
