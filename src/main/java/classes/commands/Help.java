package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
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
    public Response execute(Object inputData, String userID) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("classes.commands");
        Set<Class<? extends Commandable>> allCommands = reflections.getSubTypesOf(Commandable.class);
        StringBuilder output = new StringBuilder();
        for (Class<? extends Commandable> command : allCommands) {
                output.append(command.getDeclaredConstructor().newInstance().getInfo()).append("\n");
        }
        return new Response(0, output.toString());
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
