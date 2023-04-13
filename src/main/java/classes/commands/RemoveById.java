package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;
import java.util.UUID;

public class RemoveById extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <UUID>\t\t\t\t\t\t\t-\tудалить элемент из коллекции по его id";
    }

    @Override
    public String execute(Object inputData) {
        if (inputData instanceof String stringUTF) {
            ArrayList<Movie> movies = new CollectionManager().getCollection();
            boolean isFound = false;
            try {
                UUID uuid = UUID.fromString(stringUTF);
                for (Movie movie : movies) {
                    if (movie.getId().equals(uuid)) {
                        movies.remove(movie);
                        isFound = true;
                        break;
                    }
                }
                if (isFound)
                    return TextColor.cyan("Элемент успешно удалён");
                return TextColor.yellow("Элемент с ID=" + stringUTF + " не найден");
            } catch (IllegalArgumentException e) {
                return TextColor.yellow("Неверный формат ввода. Введите id в формате UUID");
            }
        }
        return TextColor.yellow("Неверное количество аргументов. Введите индекс в формате целочисленного числа через пробел");
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
