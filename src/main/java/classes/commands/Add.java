package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.InputHandler;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.RandomMovie;
import exceptions.WarningException;
import interfaces.Commandable;

import java.util.Objects;

public class Add extends NamedCommand implements Commandable {
    private final static boolean needInput = true;
    private final static boolean hasTransferData = true;

    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t\t-\tдобавить новый элемент в коллекцию";
    }

    @Override
    public String execute(Object inputData) {
        if (inputData != null && !(inputData instanceof String))
            return new WarningException("У команды не должно быть аргументов или аргумент \"random\"").getMessage();
        if (inputData == null) {
            InputHandler inputHandler = new InputHandler();
            // TODO: Move movie input to client. Send movie serialized object to server
            Movie movie = inputHandler.readMovie();
            CollectionManager.addMovie(movie);
        } else if (Objects.equals(inputData, "random")) {
            Movie movie = RandomMovie.generate();
            if (movie != null)
                CollectionManager.addMovie(movie);
        } else return new WarningException("У команды не должно быть аргументов или аргумент \"random\"").getMessage();
        return "Выполнено";
    }
}
