package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountGreaterThanDirector extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t-\tвывести количество элементов, значение поля director которых больше заданного";
    }

    @Override
    public String execute(String... args) {
        if (args != null && args.length == 1) {
            List<String> directorsList = new ArrayList<>();
            for (Movie movie : new CollectionManager().getCollection()) {
                directorsList.add(movie.getDirector().getName());
            }
            directorsList.add(args[0]);
            directorsList.sort(Comparator.naturalOrder());
            int count = 0;
            for (String director : directorsList) {
                if (!director.equals(args[0]))
                    count++;
                else break;
            }
            return TextColor.cyan("Число фильмов, удовлетворяющих условию: " + count);
        }
        return TextColor.yellow("Неверное количество аргументов для этой команды\n" + "Введите имя режиссёра без пробелов");
    }
}
