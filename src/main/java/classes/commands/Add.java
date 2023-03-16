package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.InputHandler;
import classes.movie.Movie;
import classes.movie.RandomMovie;
import exceptions.WarningException;
import interfaces.Commandable;

import java.util.Objects;

public class Add extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t-\tдобавить новый элемент в коллекцию";
    }

    @Override
    public void execute(String... args) {
        CollectionManager collectionManager = new CollectionManager();
        if (args == null) {
            InputHandler inputHandler = new InputHandler();
            Movie movie = inputHandler.readMovie();
            collectionManager.addMovie(movie);
        } else if (args.length == 1 && Objects.equals(args[0], "random")) {
            collectionManager.addMovie(RandomMovie.generate());
        } else new WarningException("У команды не должно быть аргументов или аргумент \"random\"").printMessage();
    }
}
