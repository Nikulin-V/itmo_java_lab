package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Coordinates;
import classes.movie.Movie;
import exceptions.GreatThanException;
import exceptions.NotGreatThanException;
import exceptions.NullValueException;
import interfaces.Commandable;

import java.util.Iterator;

public class RemoveLower extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t-\tудалить из коллекции все элементы, меньшие, чем заданный";
    }
    @Override
    public void execute(String... args) {
        if (args.length == 2) {
            try {
                Coordinates inputCoordinates = new Coordinates(Long.parseLong(args[0]), Integer.parseInt(args[1]));
                CollectionManager cm = new CollectionManager();
                cm.getCollection().removeIf(m -> inputCoordinates.compareTo(m.getCoordinates()) < 0);
                System.out.println(TextColor.cyan("Успешно удалено"));
            } catch (NotGreatThanException | GreatThanException e) {
                e.printMessage();
            } catch (NullValueException e) {
                e.printMessage();
            }
        } else System.out.println(TextColor.yellow("Некорректный ввод. Введите координаты в формате: x y"));
    }
}
