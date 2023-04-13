package classes.commands;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import interfaces.Commandable;

public class RemoveAt extends NamedCommand implements Commandable {
    public final boolean hasTransferData = true;
    @Override
    public String getInfo() {
        return getName() + " <index>\t\t\t\t\t\t\t-\tудалить элемент, находящийся в заданной позиции коллекции";
    }

    @Override
    public String execute(Object inputData) {
        if (inputData instanceof Integer) {
            try {
                int index = (Integer) inputData;
                new CollectionManager().getCollection().remove(index);
                return TextColor.cyan("Элемент успешно удалён");
            } catch (NumberFormatException e) {
                return TextColor.yellow("Неверный формат ввода. Введите индекс в формате целочисленного числа через пробел");
            } catch (IndexOutOfBoundsException e) {
                return TextColor.yellow("Введенный индекс выходит за пределы размера нынешней коллекции");
            }
        } else
            return TextColor.yellow("Неверное количество аргументов. Введите индекс в формате целочисленного числа через пробел");
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
