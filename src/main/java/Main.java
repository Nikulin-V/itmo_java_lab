import classes.collection.CollectionManager;
import classes.console.CommandHandler;
import classes.console.TextColor;
import exceptions.NoSuchCommandException;
import exceptions.SystemException;
import interfaces.Commandable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandHandler commandHandler = new CommandHandler();
        CollectionManager.readFile(args);
        System.out.print(TextColor.green("> "));
        while (scanner.hasNextLine()) {
            try {
                String inputString = scanner.nextLine();
                String commandName = inputString.split(" ")[0];
                String[] commandArgument = null;
                if (inputString.split(" ").length > 1) {
                    String[] arr = inputString.split(" ");
                    commandArgument = Arrays.copyOfRange(arr, 1, arr.length);
                }
                if (!commandName.isBlank()) {
                    Commandable command = commandHandler.getCommand(commandName);
                    command.execute(commandArgument);
                }
            } catch (NoSuchCommandException e) {
                e.printMessage();
            } catch (NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException e) {
                new SystemException().printMessage();
            }
            System.out.print(TextColor.green("> "));
        }
    }
}