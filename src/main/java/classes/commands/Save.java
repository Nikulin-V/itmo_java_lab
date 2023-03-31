package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.Movies;
import classes.xml_manager.XMLMovieManager;
import interfaces.Commandable;

import java.util.ArrayList;

public class Save extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t-\tсохранить коллекцию в файл";
    }

    @Override
    public void execute(String... args) {
        CollectionManager collectionManager = new CollectionManager();
        ArrayList<Movie> moviesList = collectionManager.getCollection();
        if (moviesList.size() != 0) {
            Movies movies = new Movies();
            movies.setMovies(moviesList);
            XMLMovieManager.getInstance().saveCollectionToXML(movies);
            System.out.println(TextColor.cyan("\tТекущая коллекция сохранена в файл"));
        } else System.out.println(TextColor.cyan("\tКоллекция пуста. Сохранять нечего"));
    }
}
