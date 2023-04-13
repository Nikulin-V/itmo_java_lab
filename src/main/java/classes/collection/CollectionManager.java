package classes.collection;

import classes.DataStorage;
import classes.console.TextColor;
import classes.movie.Coordinates;
import classes.movie.Movie;
import classes.xml_manager.XMLMovieManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;
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
            Long y1 = (long) o1.getY();
            Long x2 = o2.getX();
            Long y2 = (long) o2.getY();
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

    public static void readFile(String fileName) {

        List<Movie> enteredMovies;
        if (fileName == null) {
            System.out.println(TextColor.purple("Файл коллекции не был указан. Была выбран файл коллекции по умолчанию"));
            DataStorage.setCurrentStorageFilePath(DataStorage.DEFAULT_STORAGE_FILE_PATH);
            enteredMovies = XMLMovieManager.getInstance().readCollectionFromXML().getMovies();
        } else {
            System.out.println(TextColor.purple("Пытаюсь прочитать файл коллекции..."));
            enteredMovies = XMLMovieManager.getInstance().readCollectionFromXML(fileName).getMovies();
        }
        if (enteredMovies != null && !enteredMovies.isEmpty()) {
            for (Movie movie : enteredMovies)
                addMovie(movie);
            System.out.println(TextColor.purple("Файл коллекции был прочитан..."));
        } else System.out.println(TextColor.purple("Файл коллекции оказался пуст"));
    }
    public static void saveObject(Movie movie, SocketChannel sChannel) throws IOException {
        ObjectOutputStream  oos = new
                ObjectOutputStream(sChannel.socket().getOutputStream());
        oos.writeObject(movie);
        oos.close();
    }
}
