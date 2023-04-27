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
        String[] arg = (String[]) inputData;
        if (arg != null && String.valueOf(arg[0]).chars().allMatch(Character::isDigit)) {
            try {
                int index = Integer.parseInt(arg[0]);
                CollectionManager cm = new CollectionManager();
                if (cm.getCollection().size() >= index + 1) {
                    cm.getCollection().remove(index);
                    return TextColor.cyan("Элемент успешно удалён");
                }
                return TextColor.yellow("Элемент с индексом ") + TextColor.red(String.valueOf(index)) + TextColor.yellow(" не существует");
            } catch (NumberFormatException e) {
                return TextColor.yellow("Неверный формат ввода. Введите индекс в формате целочисленного числа через пробел");
            } catch (IndexOutOfBoundsException e) {
                return TextColor.yellow("Введенный индекс выходит за пределы размера нынешней коллекции");
            }
        }
        return TextColor.yellow("Неверное количество аргументов. Введите индекс в формате целого неотрицательного числа через пробел");
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
