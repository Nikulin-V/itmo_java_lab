package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.console.TextColor;
import interfaces.Commandable;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Help extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t\t\t\t\t\t\t\t\t\t-\tвывести справку по доступным командам";
    }

    @Override
    public Response execute(Object inputData) {
        Reflections reflections = new Reflections("classes.commands");
        Set<Class<? extends Commandable>> allCommands = reflections.getSubTypesOf(Commandable.class);
        String output = "";
        for (Class<? extends Commandable> command : allCommands) {
            try {
                output += command.getDeclaredConstructor().newInstance().getInfo() + "\n";
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                     NoSuchMethodException e) {
                return new Response(1).setData(TextColor.red("Фатальная ошибка исполнения программы"));
            }
        }
        return new Response(0).setData(output);
    }

    @Override
    public boolean isNeedInput() {
        return false;
    }

    @Override
    public boolean hasTransferData() {
        return false;
    }

}
