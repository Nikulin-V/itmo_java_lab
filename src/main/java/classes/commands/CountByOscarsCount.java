package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;

public class CountByOscarsCount extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <UUID>\t-\t вывести количество элементов, значение поля oscarsCount которых равно заданному";
    }

    @Override
    public void execute(String... args) {
        if (args.length == 1) {
            ArrayList<Movie> movies = new CollectionManager().getCollection();
            int searchMoviesCount = 0;
            try {
                int searchOscarsCount = Integer.parseInt(args[0]);
                for (Movie movie : movies) {
                    if (movie.getOscarsCount() == searchOscarsCount) {
                        searchMoviesCount += 1;
                    }
                }
                System.out.println("Количество фильмов с " + searchOscarsCount + " наградами \"Оскар\": " + searchMoviesCount);
            } catch (NumberFormatException e) {
                System.out.println(TextColor.yellow("Неверный формат ввода. \n" +
                        "Введите количество наград \"Оскар\" в формате целочисленного числа через пробел"));
            }
        } else System.out.println(TextColor.yellow("Неверное количество аргументов. \n" +
                "Введите количество наград \"Оскар\" в формате целочисленного числа через пробел"));
    }
}
