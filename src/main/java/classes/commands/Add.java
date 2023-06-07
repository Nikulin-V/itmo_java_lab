package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.RandomMovie;
import classes.sql_managers.SQLManager;
import interfaces.Commandable;

public class Add extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t\t-\tдобавить новый элемент в коллекцию";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        if (inputData instanceof Movie movie) {
            if (SQLManager.insertMovie(movie))
                CollectionManager.addMovie(movie);
        } else if (inputData instanceof String[] arg && arg[0].equals("random")) {
            Movie randomMovie = RandomMovie.generate(userID);
            if (randomMovie == null)
                return new Response(1, TextColor.grey("Ошибка при генерации объекта"));
            if (SQLManager.insertMovie(randomMovie))
                CollectionManager.addMovie(randomMovie);
            else{
                System.out.println(TextColor.purple("Атомарная операция не завершена: не удалось занести объект в базу данных"));
            }
        } else return new Response(1, TextColor.purple("У команды не должно быть аргументов или " +
                "аргумент \"random\""));
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
