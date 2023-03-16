package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;

public class PrintDescending extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t-\tвывести элементы коллекции в порядке убывания";
    }

    @Override
    public void execute(String... args) {
        CollectionManager collectionManager = new CollectionManager();
        ArrayList<Movie> collection = new ArrayList<>(collectionManager.getCollection());
        CollectionManager.sort(collection);
        System.out.println(TextColor.cyan("Содержимое коллекции в порядке убывания: "));
        if (collection.size() != 0)
            for (Movie movie : collection)
                System.out.println("\t" + movie.toString());
        else System.out.println(TextColor.cyan("\tПусто"));
    }
}
