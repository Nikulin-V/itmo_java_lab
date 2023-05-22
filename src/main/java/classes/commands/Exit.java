package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.console.TextColor;
import interfaces.Commandable;

public class Exit extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tзавершить программу (без сохранения в файл)";
    }

    @Override
    public Response execute(Object inputData, String userID) {
        System.out.println("Завершение работы...");
        Runtime.getRuntime().exit(0);
        return new Response(0).setData(TextColor.green("Выполнено"));
    }
}
