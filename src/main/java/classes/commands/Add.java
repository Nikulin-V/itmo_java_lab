package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.InputHandler;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.RandomMovie;
import interfaces.Commandable;

import java.util.Objects;

public class Add extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t-\tдобавить новый элемент в коллекцию";
    }

    @Override
    public void execute(String... args) {
        CollectionManager collectionManager = new CollectionManager();
        if (args != null && (Objects.equals(args[0], "random"))) {
            if (args.length == 2) {
                int m = Integer.parseInt(args[1]);
                for (int i = 0; i < m; i++) {
                    collectionManager.addMovie(RandomMovie.generate());
                }
            } else if (args.length == 1) {
                collectionManager.addMovie(RandomMovie.generate());
            } else {
                System.out.println(TextColor.yellow("Неверное количество аргументов для этой команды"));
            }
        } else {
            InputHandler inputHandler = new InputHandler();
            Movie movie = inputHandler.readMovie();
            collectionManager.addMovie(movie);
        }
    }

}
