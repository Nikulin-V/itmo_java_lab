package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.RandomMovie;
import interfaces.Commandable;

public class Add extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t\t-\tдобавить новый элемент в коллекцию";
    }

    @Override
    public Response execute(Object inputData) {
        if (inputData instanceof Movie movie) {
            CollectionManager.addMovie(movie);
        } else if (inputData instanceof String[] arg && arg[0].equals("random")) {
            CollectionManager.addMovie(RandomMovie.generate());
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
