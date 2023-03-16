package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Coordinates;
import classes.movie.Movie;
import interfaces.Commandable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrintDescending extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t-\tвывести элементы коллекции в порядке убывания";
    }

    @Override
    public void execute(String... args) {
        List<Coordinates> moviesCoordinatesList = new ArrayList<>();
        CollectionManager collectionManager = new CollectionManager();
        for (Movie movies : collectionManager.getCollection()) {
            moviesCoordinatesList.add(movies.getCoordinates());
        }
        moviesCoordinatesList.sort((o1, o2) -> {
            Long x1 = o1.getX();
            Long y1 = (long) o1.getY();
            Long x2 = o2.getX();
            Long y2 = (long) o2.getY();
            return !x1.equals(x2) ? x1.compareTo(x2) : y1.compareTo(y2);
        });
        for (int i = 0; i < moviesCoordinatesList.size(); i++) {
            Coordinates m = moviesCoordinatesList.get(i);
            ArrayList<Movie> collection = collectionManager.getCollection();
            for (int j = 0; j < collection.size(); j++) {
                Movie movies = collection.get(j);
                if (m.equals(movies.getCoordinates())) {
                    Collections.swap(collectionManager.getCollection(), i, j);
                }
            }
        }
        System.out.println(TextColor.cyan("Содержимое коллекции в порядке убывания: "));

        ArrayList<Movie> movies = collectionManager.getCollection();
        if (movies.size() != 0)
            for (Movie movie : movies)
                System.out.println("\t" + movie.toString());
        else System.out.println(TextColor.cyan("\tПусто"));
    }
}
