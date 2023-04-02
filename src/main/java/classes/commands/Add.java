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
    public String execute(String... args) {
        if (args == null) {
            InputHandler inputHandler = new InputHandler();
            // TODO: Move movie input to client. Send movie serialized object to server
            Movie movie = inputHandler.readMovie();
            CollectionManager.addMovie(movie);
        } else if (args.length == 1 && Objects.equals(args[0], "random")) {
            Movie movie = RandomMovie.generate();
            if (movie != null)
                CollectionManager.addMovie(movie);
        } else return new WarningException("У команды не должно быть аргументов или аргумент \"random\"").getMessage();
        return "Выполнено";
    }
}
