package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import interfaces.Commandable;

public class Sort extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t\t\t\t\t\t-\tотсортировать коллекцию в естественном порядке";
    }

    @Override
    public Response execute(Object inputData) {
        CollectionManager collectionManager = new CollectionManager();
        if (!collectionManager.getCollection().isEmpty()) {
            CollectionManager.sort(collectionManager.getCollection());
            return new Response(0).setData(TextColor.cyan("Коллекция успешно отсортирована в порядке убывания"));
        }
        return new Response(0).setData(TextColor.cyan("Коллекция пустая, нечего сортировать"));
    }
}
