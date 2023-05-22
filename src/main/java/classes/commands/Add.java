package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.RandomMovie;
import classes.sql_managers.SQLManager;
import interfaces.Commandable;

import java.sql.ResultSet;
import java.util.UUID;

public class Add extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t\t-\tдобавить новый элемент в коллекцию";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        if (inputData instanceof Movie movie) {

            CollectionManager.addMovie(movie);
        } else if (inputData instanceof String[] arg && arg[0].equals("random")) {
            ResultSet rs = SQLManager.executeQuery("df");
            CollectionManager.addMovie(RandomMovie.generate(UUID.randomUUID()));
        } else return new Response(1).setData(TextColor.purple("У команды не должно быть аргументов или " +
                "аргумент \"random\""));
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
