package classes.collection;

import classes.console.TextColor;
import classes.movie.Coordinates;
import classes.movie.Movie;
import classes.sql_managers.SQLManager;
import exceptions.*;

import java.sql.SQLException;
import java.util.*;

public class CollectionManager {
    private static final ArrayList<Movie> collection = new ArrayList<>();
    private static final String type = "ArrayList";
    private static final Date initDate = new Date();

    public ArrayList<Movie> getCollection() {
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

    public void removeMovie(UUID MovieId) {
        for (Movie movie : CollectionManager.collection) {
            if (movie.getId() == MovieId) {
                CollectionManager.collection.remove(movie);
                break;
            }
        }
    }

    public void removeMovie(Movie movie) {
        collection.remove(movie);
    }

    public void removeMovie(int movieIndex) {
        collection.remove(movieIndex);
    }

    public void clear() {
        collection.clear();
    }

    public static void sort(ArrayList<Movie> collection) {

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
        List<Movie> enteredMovies = null;
        try {
            movies = SQLManager.executeQuery("SELECT * FROM movies"); // TODO: (WHERE userID == currentUserID)
        } catch (NotGreatThanException | GreatThanException | NullValueException | BlankValueException |
                 BadValueLengthException | NotUniqueException | SQLException e) {
            e.printStackTrace();
        }
        if (enteredMovies != null && !enteredMovies.isEmpty()) {
            for (Movie movie : enteredMovies)
                addMovie(movie);
            System.out.println(TextColor.purple("База данных был прочитана..."));
        } else System.out.println(TextColor.purple("База данных оказалась пуста"));
    }
}
