package classes.commands;

import classes.NamedCommand;
import classes.console.CommandHandler;
import classes.console.TextColor;
import exceptions.DangerException;
import exceptions.NoSuchCommandException;
import exceptions.WarningException;
import interfaces.Commandable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ExecuteScript extends NamedCommand implements Commandable {
    public final static int MAX_SCRIPT_TRANSITION_COUNT = 100;
    public static int scriptTransitionCount = 0;

    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t-\tсчитать и исполнить скрипт из указанного файла";
    }

    @Override
    public void execute(String... args) {
        if (args.length == 1) {
            try {
                File file = new File(args[0]);
                assert file.exists() && file.isFile();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                CommandHandler commandHandler = new CommandHandler();
                for (Object line : reader.lines().toArray())
                    try {
                        String inputString = (String) line;
                        String commandName = inputString.split(" ")[0];
                        String commandArgument = null;
                        if (inputString.split(" ").length > 1) {
                            commandArgument = inputString.split(" ")[1];
                        }
                        Commandable command = commandHandler.getCommand(commandName);
                        if (Objects.equals(command.getName(), "execute_script"))
                            scriptTransitionCount += 1;
                        if (scriptTransitionCount > MAX_SCRIPT_TRANSITION_COUNT) {
                            new WarningException("Произошло зацикливание выполнения скриптов. Программа остановлена").printMessage();
                            scriptTransitionCount = 0;
                            break;
                        }
                        command.execute(commandArgument);
                    } catch (NoSuchCommandException | InvocationTargetException | NoSuchMethodException |
                             InstantiationException
                             | IllegalAccessException ignored) {
                    }
            } catch (AssertionError | FileNotFoundException e) {
                new DangerException("Файл не найден").printMessage();
                scriptTransitionCount = 0;
            }
        } else System.out.println(TextColor.yellow("Неверное количество аргументов. Введите имя файла через пробел"));
    }
}
