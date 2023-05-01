package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

public class Update extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tобновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public Response execute(Object inputData) {
        try {
            boolean founded = false;
            if (inputData instanceof Movie newMovie) {
                Movie changingMovie = null;
                CollectionManager cm = new CollectionManager();
                for (int i = 0; i < cm.getCollection().size(); i++) {
                    if (cm.getCollection().get(i).getId().equals(newMovie.getId())) {
                        founded = true;
                        cm.getCollection().remove(i);
                        cm.getCollection().add(newMovie);
                        break;
                    }
                }
                if (!founded) return new Response(0).setData(TextColor.yellow("Movie с UUID=") +
                        TextColor.red(newMovie.getId().toString()) + TextColor.yellow(" не существует"));
            } else throw new IllegalArgumentException();
        } catch (IllegalArgumentException exception) {
            return new Response(1).setData(TextColor.yellow("Некорректно введён UUID фильма, повторите попытку"));
        }
        return new Response(0).setData(TextColor.green("Выполнено"));
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
