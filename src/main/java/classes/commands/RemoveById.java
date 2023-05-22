package classes.commands;

import classes.Response;
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
    public Response execute(Object inputData, String userID) {

        if (inputData instanceof String[] arg) {
            ArrayList<Movie> movies = new CollectionManager().getCollection();
            boolean isFound = false;

                UUID uuid = UUID.fromString(arg[0]);
                for (Movie movie : movies) {
                    if (movie.getId().equals(uuid)) {
                        if (movie.getUserID().equals(userID)) movies.remove(movie);
                        else return new Response(0).setData(TextColor.yellow("Нет прав доступа для выполнения команды"));
                        isFound = true;
                        break;
                    }
                }
                if (isFound)
                    return new Response(0).setData(TextColor.cyan("Элемент успешно удалён"));
                return new Response(0).setData(TextColor.yellow("Элемент с ID=" + arg[0] + " не найден"));
        }
        return new Response(1).setData(TextColor.yellow("Неверное количество аргументов. Введите id в " +
                "формате UUID через пробел"));
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
