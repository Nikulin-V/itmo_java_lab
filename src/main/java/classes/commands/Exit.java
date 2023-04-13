package classes.commands;

import classes.abs.NamedCommand;
import interfaces.Commandable;

public class Exit extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tзавершить программу (без сохранения в файл)";
    }

    @Override
    public String execute(Object inputData) {
        System.out.println("Завершение работы...");
        Runtime.getRuntime().exit(0);
        return "Выполнено";
    }
}
