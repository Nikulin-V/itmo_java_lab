package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import interfaces.Commandable;

public class Info extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tвывести  информацию о коллекции";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        CollectionManager collectionManager = new CollectionManager();
        return new Response(0,
                "Тип коллекции: " + CollectionManager.getType() + "\n" +
                        "Дата инициализации: " + CollectionManager.getInitDate() + "\n" +
                        "Количество элементов: " + collectionManager.getCollection().size()
        );
    }
}
