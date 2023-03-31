package classes.commands;

import classes.NamedCommand;
import interfaces.Commandable;

public class Exit extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t-\tзавершить программу (без сохранения в файл)";
    }

    @Override
    public void execute(String... args) {
        System.out.println("Завершение работы...");
        Runtime.getRuntime().exit(0);
    }
}
