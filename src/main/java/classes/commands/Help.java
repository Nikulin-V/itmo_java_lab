package classes.commands;

import classes.NamedCommand;
import interfaces.Commandable;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Help extends NamedCommand implements Commandable {
    @Override
    public String getInfo() {
        return getName() + "\t-\tвывести справку по доступным командам";
    }

    @Override
    public void execute(String... args) {
        Reflections reflections = new Reflections("classes.commands");
        Set<Class<? extends Commandable>> allCommands = reflections.getSubTypesOf(Commandable.class);
        for (Class<? extends Commandable> command : allCommands) {
            try {
                System.out.println(command.getDeclaredConstructor().newInstance().getInfo());
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
