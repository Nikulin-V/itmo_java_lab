package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.movie.Movie;
import classes.movie.RandomMovie;
import exceptions.WarningException;
import interfaces.Commandable;

public class Add extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t\t-\tдобавить новый элемент в коллекцию";
    }

    @Override
    public String execute(Object inputData) {
        if (!(inputData instanceof Movie || inputData instanceof String))
            return new WarningException("У команды не должно быть аргументов или аргумент \"random\"").getMessage();
        if (inputData instanceof Movie movie) {
            CollectionManager.addMovie(movie);
        } else if (inputData.equals("random")) {
            CollectionManager.addMovie(RandomMovie.generate());
        } else return new WarningException("У команды не должно быть аргументов или аргумент \"random\"").getMessage();
        return "Выполнено";
    }

    @Override
    public boolean isNeedInput() {
        return true;
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
