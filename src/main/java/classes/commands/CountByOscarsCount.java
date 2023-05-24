package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;

public class CountByOscarsCount extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <int>\t\t\t\t\t-\tвывести количество элементов, значение поля oscarsCount которых равно заданному";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        String[] arg = (String[]) inputData;
        if (arg != null && String.valueOf(arg[0]).chars().allMatch(Character::isDigit)) {
            ArrayList<Movie> movies = new CollectionManager().getCollection();
            int searchMoviesCount = 0;
                int searchOscarsCount = Integer.parseInt(arg[0]);
                for (Movie movie : movies) {
                    if (movie.getOscarsCount() == searchOscarsCount) {
                        searchMoviesCount += 1;
                    }
                }
                return new Response(0, TextColor.cyan("Количество фильмов с " +
                        searchOscarsCount + " наградами \"Оскар\": " + searchMoviesCount));
        }
        return new Response(1, TextColor.yellow("Неверный формат ввода. \n" +
                "Введите количество наград \"Оскар\" в формате целочисленного числа через пробел"));
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
