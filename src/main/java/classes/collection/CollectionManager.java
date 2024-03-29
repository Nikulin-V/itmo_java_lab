package classes.collection;

import classes.console.TextColor;
import classes.movie.Coordinates;
import classes.movie.Movie;
import classes.sql_managers.SQLManager;
import exceptions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CollectionManager {
    private static final List<Movie> collection = Collections.synchronizedList(new ArrayList<>());

    private static final String type = "ArrayList";
    private static final Date initDate = new Date();

    public List<Movie> getCollection() {
        return CollectionManager.collection;
    }

    public static String getType() {
        return type;
    }

    public static Date getInitDate() {
        return initDate;
    }

    public static void addMovie(Movie movie) {
        CollectionManager.collection.add(movie);
    }

    public static void clear() {
        CollectionManager.collection.clear();
    }

    public static void sort(List<Movie> collection) {
        List<Coordinates> moviesCoordinatesList = new ArrayList<>();
        for (Movie movies : collection)
            moviesCoordinatesList.add(movies.getCoordinates());
        moviesCoordinatesList.sort((o1, o2) -> {
            Long x1 = o1.getX();
            Long y1 = o1.getY();
            Long x2 = o2.getX();
            Long y2 = o2.getY();
            return !x1.equals(x2) ? x1.compareTo(x2) : y1.compareTo(y2);
        });
        for (int i = 0; i < moviesCoordinatesList.size(); i++) {
            Coordinates m = moviesCoordinatesList.get(i);

            for (int j = 0; j < collection.size(); j++) {
                Movie movies = collection.get(j);
                if (m.equals(movies.getCoordinates()))
                    Collections.swap(collection, i, j);
            }
        }
    }

    public static void readDB() {
        List<Movie> moviesList = null;
        try {
            moviesList = SQLManager.executeGetMovies();
        } catch (BlankValueException | NotGreatThanException | BadValueLengthException | NotUniqueException |
                 GreatThanException | NullValueException e) {
            //ignored
        }
        if (moviesList == null || moviesList.isEmpty()) {
            System.out.println(TextColor.purple("База данных оказалась пуста"));
            CollectionManager.clear();
        } else {
            for (Movie movie : moviesList)
                addMovie(movie);
            System.out.println(TextColor.purple("База данных был прочитана..."));
        }
    }
}
