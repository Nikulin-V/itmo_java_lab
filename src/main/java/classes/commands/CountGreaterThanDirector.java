package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;
import java.util.List;

public class CountGreaterThanDirector extends NamedCommand implements Commandable {
    private final static boolean needInput = true;

    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t-\tвывести количество элементов, значение поля director которых больше заданного";
    }

    @Override
    public String execute(Object inputData) {
        if (inputData instanceof String) {
            String referenceDirector = (String) inputData;
            List<String> directorsList = new ArrayList<>();
            for (Movie movie : new CollectionManager().getCollection()) {
                directorsList.add(movie.getDirector().getName());
            }
            directorsList.add(referenceDirector);
            long count = directorsList
                    .stream()
                    .filter(director -> !director.equals(referenceDirector))
                    .count();
            return TextColor.cyan("Число фильмов, удовлетворяющих условию: " + count);
        }
        return TextColor.yellow("Неверное количество аргументов для этой команды\n" + "Введите имя режиссёра без пробелов");
    }
}
