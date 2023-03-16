package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Coordinates;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sort extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t-\tотсортировать коллекцию в естественном порядке";
    }

    @Override
    public void execute(String... args) {
        List<Coordinates> moviesCoordinatesList = new ArrayList<>();
        CollectionManager collectionManager = new CollectionManager();
        if (!collectionManager.getCollection().isEmpty()) {
           CollectionManager.sort(collectionManager.getCollection());
            System.out.println(TextColor.cyan("Коллекция успешно отсортирована в порядке убывания"));
        } else System.out.println(TextColor.cyan("Коллекция пустая, нечего сортировать"));
    }
}
