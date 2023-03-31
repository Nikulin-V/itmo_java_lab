package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;
import java.util.UUID;

public class RemoveById extends NamedCommand implements Commandable {

    @Override
    public String getInfo() {
        return getName() + " <UUID>\t-\tудалить элемент из коллекции по его id";
    }

    @Override
    public void execute(String... args) {
        if (args.length == 1) {
            ArrayList<Movie> movies = new CollectionManager().getCollection();
            boolean isFound = false;
            try {
                UUID uuid = UUID.fromString(args[0]);
                for (Movie movie : movies) {
                    if (movie.getId().equals(uuid)) {
                        movies.remove(movie);
                        isFound = true;
                        break;
                    }
                }
                if (isFound)
                    System.out.println(TextColor.cyan("Элемент успешно удалён"));
                else System.out.println(TextColor.yellow("Элемент с ID=" + args[0] + " не найден"));
            } catch (IllegalArgumentException e) {
                System.out.println(TextColor.yellow("Неверный формат ввода. Введите id в формате UUID"));
            }
        } else
            System.out.println(TextColor.yellow("Неверное количество аргументов. Введите индекс в формате целочисленного числа через пробел"));
    }
}
