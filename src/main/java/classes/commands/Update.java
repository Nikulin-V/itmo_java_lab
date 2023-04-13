package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.InputHandler;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.UUID;

public class Update extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tобновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String execute(Object inputData) {
        Movie changingMovie = null;
        try {
            if (inputData instanceof String stringUTF) {
                UUID uuid = UUID.fromString(stringUTF);
                CollectionManager cm = new CollectionManager();
                for (Movie movie : cm.getCollection()) {
                    if (movie.getId().equals(uuid)) {
                        changingMovie = movie;
                        break;
                    }
                }
                if (changingMovie != null) {
                    new InputHandler().updateMovie(changingMovie);
                } else return TextColor.yellow("Не найден фильм с введённым UUID");
            } else throw new IllegalArgumentException();
        } catch (IllegalArgumentException exception) {
            return TextColor.yellow("Некорректно введён UUID фильма, повторите попытку");
        }
        return "Выполнено";
    }

    @Override
    public boolean isNeedInput() {
        return true;
    }
}
