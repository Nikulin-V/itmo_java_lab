package classes.commands;

import classes.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;
import interfaces.Commandable;

public class Info extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t-\tдобавить новый элемент в коллекцию";
    }

    @Override
    public void execute(String... args) {
        CollectionManager collectionManager = new CollectionManager();
        System.out.println(TextColor.cyan(
                "Информация о коллекции:\n" +
                        "\tТип коллекции: " + CollectionManager.getType() + "\n" +
                        "\tДата инициализации: " + CollectionManager.getInitDate() + "\n" +
                        "\tКоличество элементов: " + collectionManager.getCollection().size()
        ));
    }

}
