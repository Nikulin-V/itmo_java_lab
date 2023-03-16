package classes.console;

import exceptions.NoSuchCommandException;
import interfaces.Commandable;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * Class, which manages implemented commands
 */
public class CommandHandler {
    private final Set<Class<? extends Commandable>>
            allCommands = new Reflections("classes.commands").getSubTypesOf(Commandable.class);

    /**
     * @param commandName String with name of command
     * @return Command instance
     */
    public Commandable getCommand(String commandName) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchCommandException {
        for (Class<? extends Commandable> command : allCommands) {
            if (camelToSnake(command.getName().split("\\.")[2]).equals(commandName))
                return command.getDeclaredConstructor().newInstance();
        }
        throw new NoSuchCommandException();
    }

    /**
     * Converts camel case string to snake case
     *
     * @param str String in camel case
     * @return String in snake case
     */
    public static String camelToSnake(String str) {
        String result = str.substring(0, 1).toLowerCase();
        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch))
                result += "_" + Character.toLowerCase(ch);
            else result += ch;
        }
        return result;
    }
}
