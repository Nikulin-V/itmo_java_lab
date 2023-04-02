package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Coordinates;
import interfaces.Commandable;

import java.util.ArrayList;
import java.util.List;

public class Sort extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t-\tотсортировать коллекцию в естественном порядке";
    }

    @Override
    public String execute(String... args) {
        CollectionManager collectionManager = new CollectionManager();
        if (!collectionManager.getCollection().isEmpty()) {
           CollectionManager.sort(collectionManager.getCollection());
            return TextColor.cyan("Коллекция успешно отсортирована в порядке убывания");
        } return TextColor.cyan("Коллекция пустая, нечего сортировать");
    }
}
