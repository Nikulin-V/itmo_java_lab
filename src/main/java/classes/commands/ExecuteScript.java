package classes.commands;

import classes.abs.NamedCommand;
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
        return getName() + " <file_name>\t\t\t\t\t-\tсчитать и исполнить скрипт из указанного файла";
    }

    @Override
    public String execute(Object inputData) {
        if (inputData instanceof String scriptName) {
            try {
                File file = new File(scriptName);
                assert file.exists() && file.isFile();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                CommandHandler commandHandler = new CommandHandler();
                for (Object line : reader.lines().toArray())
                    try {
                        String inputString = (String) line;
                        String commandName = inputString.split(" ")[0];
                        String commandArgument = null;
                        if (inputString.split(" ").length > 1)
                            commandArgument = inputString.split(" ")[1];
                        Commandable command = commandHandler.getCommand(commandName);
                        if (Objects.equals(command.getName(), "execute_script"))
                            scriptTransitionCount += 1;
                        if (scriptTransitionCount > MAX_SCRIPT_TRANSITION_COUNT) {
                            scriptTransitionCount = 0;
                            return new WarningException("Произошло зацикливание выполнения скриптов. Программа остановлена").getMessage();
                        }
                        return command.execute(commandArgument);
                    } catch (NoSuchCommandException | InvocationTargetException | NoSuchMethodException |
                             InstantiationException | IllegalAccessException e) {
                        return e.getMessage();
                    }
            } catch (AssertionError | FileNotFoundException e) {
                scriptTransitionCount = 0;
                return new DangerException("Файл не найден").getMessage();
            }
        }
        return TextColor.yellow("Неверное количество аргументов. Введите имя файла через пробел");
    }
}
