package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.InputHandler;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

public class Update extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tобновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public String execute(Object inputData) {
        try {
            if (inputData instanceof Movie newMovie) {
                Movie changingMovie = null;
                CollectionManager cm = new CollectionManager();
                for (Movie collectionMovie : cm.getCollection()) {
                    if (collectionMovie.getId().equals(newMovie.getId())) {
                        changingMovie = collectionMovie;
                        break;
                    }
                }
                if (changingMovie != null) {
                    new InputHandler().updateMovie(changingMovie);
                } else
                    return TextColor.yellow("Movie с UUID=") + TextColor.red(newMovie.getId().toString()) + TextColor.yellow(" не существует");
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

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
