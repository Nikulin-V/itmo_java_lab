package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.sql_managers.SQLManager;
import interfaces.Commandable;

public class Update extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <UUID>\t\t\t\t\t\t\t\t\t-\tобновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public Response execute(Object inputData, String userID) {
            boolean founded = false;
            if (inputData instanceof Movie newMovie) {
                if(!newMovie.getUserID().equals(userID)) return new Response(1).setData(TextColor.yellow("Нет прав доступа для выполнения команды"));
                SQLManager.executeMovieUpdate(newMovie);
                CollectionManager cm = new CollectionManager();
                for (int i = 0; i < cm.getCollection().size(); i++) {
                    if (cm.getCollection().get(i).getId().equals(newMovie.getId())) {
                        founded = true;
                        cm.getCollection().remove(i);
                        cm.getCollection().add(newMovie);
                        break;
                    }
                }
                if (!founded) return new Response(0, TextColor.yellow("Movie с UUID=") +
                        TextColor.red(newMovie.getId().toString()) + TextColor.yellow(" не существует"));
            } else return new Response(1, TextColor.yellow("Некорректно введён UUID фильма, повторите попытку"));
        return new Response(0, TextColor.green("Выполнено"));
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
