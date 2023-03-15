package classes.collection;

import classes.movie.Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class CollectionManager {
    private static ArrayList<Movie> collection = null;
    private static final String type = "ArrayList";
    private static final Date initDate = new Date();

    public CollectionManager() {
        if (CollectionManager.collection == null)
            CollectionManager.collection = new ArrayList<>();
    }

    public ArrayList<Movie> getCollection() {
        return CollectionManager.collection;
    }

    public static String getType() {
        return type;
    }

    public static Date getInitDate() {
        return initDate;
    }

    public void addMovie(Movie movie) {
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

    public void clear(){
        collection.clear();
    }
}
