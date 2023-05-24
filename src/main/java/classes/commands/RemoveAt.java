package classes.commands;

import classes.Response;
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
    public Response execute(Object inputData, String userID) {
        String[] arg = (String[]) inputData;
        if (arg != null && String.valueOf(arg[0]).chars().allMatch(Character::isDigit)) {
                int index = Integer.parseInt(arg[0]);
                CollectionManager cm = new CollectionManager();
                if (cm.getCollection().size() >= index + 1) {
                    if (cm.getCollection().get(index).getUserID().equals(userID)) cm.getCollection().remove(index);
                    else return new Response(0, TextColor.yellow("Нет прав доступа для выполнения команды"));
                    return new Response(0, TextColor.cyan("Элемент успешно удалён"));
                }
            return new Response(0, TextColor.yellow("Элемент с индексом ") +
                    TextColor.red(String.valueOf(index)) + TextColor.yellow(" не существует"));
        }
        return new Response(1, TextColor.yellow("Неверное количество аргументов. Введите индекс в " +
                "формате целого неотрицательного числа через пробел"));
    }

    @Override
    public boolean hasTransferData() {
        return true;
    }
}
