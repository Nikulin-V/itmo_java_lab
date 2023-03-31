package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.InputHandler;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.UUID;

public class Update extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t-\tобновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void execute(String... args) {
        Movie changingMovie = null;
        try {
            UUID uuid = UUID.fromString(args[0]);
            CollectionManager cm = new CollectionManager();
            for (Movie movie : cm.getCollection()) {
                if (movie.getId().equals(uuid)) {
                    changingMovie = movie;
                    break;
                }
            }
            if (changingMovie != null) {
                new InputHandler().updateMovie(changingMovie);
            } else System.out.println(TextColor.yellow("Не найден фильм с введённым UUID"));
        } catch (IllegalArgumentException exception) {
            System.out.println(TextColor.yellow("Некорректно введён UUID фильма, повторите попытку"));
        }
    }
}
