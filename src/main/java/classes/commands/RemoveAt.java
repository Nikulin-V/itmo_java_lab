package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import interfaces.Commandable;

public class RemoveAt extends NamedCommand implements Commandable {

    @Override
    public String getInfo() {
        return getName() + " <index>\t\t\t\t\t\t\t-\tудалить элемент, находящийся в заданной позиции коллекции";
    }

    @Override
    public String execute(String... args) {
        if (args.length == 1) {
            try {
                int index = Integer.parseInt(args[0]);
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
}
