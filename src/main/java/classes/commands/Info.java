package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import interfaces.Commandable;

public class Info extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tвывести  информацию о коллекции";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        CollectionManager collectionManager = new CollectionManager();
        return new Response(0).setData(TextColor.cyan(
                "Информация о коллекции:\n" +
                        "\tТип коллекции: " + CollectionManager.getType() + "\n" +
                        "\tДата инициализации: " + CollectionManager.getInitDate() + "\n" +
                        "\tКоличество элементов: " + collectionManager.getCollection().size()
        ));
    }
}
