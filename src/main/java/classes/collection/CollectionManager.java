package classes.collection;

import classes.console.TextColor;
import classes.movie.*;
import classes.sql_managers.SQLManager;
import exceptions.*;

import java.sql.ResultSet;
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
    public static void clear() {
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
        List<Movie> enteredMovies = new ArrayList<>();
        try {
            // TODO: (WHERE userID == currentUserID)

            ResultSet moviesResultSet = SQLManager.executeQuery("SELECT * FROM movies");
            if (moviesResultSet == null) {
                CollectionManager.clear();
            } else {
                while (moviesResultSet.next()) {
                    Person director = null;
                    UUID id_director = (UUID) moviesResultSet.getObject("uuid_director");
                    //TODO возможно отправлять uuid->string в БД не лучшая идея, тогда надо preparedstatement не только для одной строки писать
                    ResultSet directorResultSet = SQLManager.executePreparedQuery("SELECT * FROM directors WHERE uuid_director=",id_director.toString());
                    if (directorResultSet != null && directorResultSet.next()) {
                        director = new Person(
                                (UUID) directorResultSet.getObject("uuid_director"),
                                directorResultSet.getString("name"),
                                directorResultSet.getDate("birthday"),
                                directorResultSet.getDouble("height"),
                                directorResultSet.getString("passport_id"),
                                Color.getById(directorResultSet.getInt("eye_color"))
                        );
                        directorResultSet.close();
                    }
                    Coordinates coordinates = new Coordinates(moviesResultSet.getInt("coordinatex"),
                            moviesResultSet.getInt("coordinatey"));
                    Movie movie = new Movie(
                            (UUID) moviesResultSet.getObject("uuid_id"),
                            moviesResultSet.getString("name"),
                            coordinates,
                            moviesResultSet.getDate("creation_date"),
                            moviesResultSet.getLong("oscars_count"),
                            moviesResultSet.getLong("golden_palm_count"),
                            moviesResultSet.getFloat("budget"),
                            MpaaRating.getById(moviesResultSet.getInt("id_mpaarating")),
                            director,
                            moviesResultSet.getString("login")
                    );
                    enteredMovies.add(movie);
                }
                moviesResultSet.close();
            }



        } catch (NotGreatThanException | GreatThanException | NullValueException | BlankValueException |
                 BadValueLengthException | NotUniqueException | SQLException e) {
            e.printStackTrace();
        }
        if (!enteredMovies.isEmpty()) {
            for (Movie movie : enteredMovies)
                addMovie(movie);
            System.out.println(TextColor.purple("База данных был прочитана..."));
        } else System.out.println(TextColor.purple("База данных оказалась пуста"));
    }
}
