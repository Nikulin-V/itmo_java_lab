package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import classes.movie.Coordinates;
import exceptions.GreatThanException;
import exceptions.NotGreatThanException;
import exceptions.NullValueException;
import interfaces.Commandable;

public class RemoveLower extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t-\tудалить из коллекции все элементы, меньшие, чем заданный";
    }
    @Override
    public String execute(Object inputData) {
        if (inputData instanceof String[] coordinatesArray && coordinatesArray.length == 2) {
            try {
                long x = Long.parseLong(coordinatesArray[0]);
                long y = Long.parseLong(coordinatesArray[1]);
                Coordinates inputCoordinates = new Coordinates(x, y);
                CollectionManager cm = new CollectionManager();
                cm.getCollection().removeIf(m -> inputCoordinates.compareTo(m.getCoordinates()) < 0);
                return TextColor.cyan("Успешно удалено");
            } catch (NotGreatThanException | GreatThanException e) {
                return e.getMessage();
            } catch (NullValueException e) {
                return e.getMessage();
            } catch (NumberFormatException ignored) {
            }
        }
        return TextColor.yellow("Некорректный ввод. Введите координаты в формате чисел: x y");
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
