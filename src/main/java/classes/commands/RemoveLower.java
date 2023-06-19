package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Coordinates;
import classes.movie.Movie;
import classes.sql_managers.SQLManager;
import exceptions.GreatThanException;
import exceptions.NotGreatThanException;
import exceptions.NullValueException;
import interfaces.Commandable;

import java.util.ArrayList;

public class RemoveLower extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t-\tудалить из коллекции все элементы, меньшие, чем заданный";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        if (inputData instanceof String[] coordinatesArray && coordinatesArray.length == 2) {
            try {
                int x = Integer.parseInt(coordinatesArray[0]);
                int y = Integer.parseInt(coordinatesArray[1]);
                Coordinates inputCoordinates = new Coordinates(x, y);
                CollectionManager cm = new CollectionManager();
                ArrayList<Movie> movieToDelete = (ArrayList<Movie>) cm.getCollection()
                        .stream()
                        .filter(m -> inputCoordinates.compareTo(m.getCoordinates()) < 0 &&
                                m.getUserID().equals(userID))
                        .toList();
                for (Movie movie : movieToDelete)
                    if (movie.getUserID().equals(userID)) {
                        SQLManager.executeMovieDelete(movie.getId(), userID);
                        cm.getCollection().remove(movie);
                    }


                return new Response(0).setData(TextColor.cyan("Выполнено"));
            } catch (NotGreatThanException | GreatThanException e) {
                return new Response(1, e.getMessage());
            } catch (NullValueException e) {
                return new Response(1, e.getMessage());
            } catch (NumberFormatException ignored) {
            }
        }
        return new Response(1, TextColor.yellow("Некорректный ввод. Введите координаты в " +
                "формате чисел: x y"));
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
