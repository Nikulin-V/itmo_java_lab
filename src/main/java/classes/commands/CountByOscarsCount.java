package classes.commands;

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
    public String execute(Object inputData) {
        String[] arg = (String[]) inputData;
        if (arg != null && String.valueOf(arg[0]).chars().allMatch(Character::isDigit)) {
            ArrayList<Movie> movies = new CollectionManager().getCollection();
            int searchMoviesCount = 0;
            try {
                int searchOscarsCount = Integer.parseInt(arg[0]);
                for (Movie movie : movies) {
                    if (movie.getOscarsCount() == searchOscarsCount) {
                        searchMoviesCount += 1;
                    }
                }
                return "Количество фильмов с " + searchOscarsCount + " наградами \"Оскар\": " + searchMoviesCount;
            } catch (NumberFormatException e) {
                return TextColor.yellow("Неверный формат ввода. \n" +
                        "Введите количество наград \"Оскар\" в формате целочисленного числа через пробел");
            }
        }
        return TextColor.yellow("Неверное количество аргументов. \n" +
                "Введите количество наград \"Оскар\" в формате целочисленного числа через пробел");
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
