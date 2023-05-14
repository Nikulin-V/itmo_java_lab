package classes.commands;

import classes.abs.NamedCommand;
import classes.console.CommandHandler;
import classes.console.TextColor;
import exceptions.DangerException;
import exceptions.NoSuchCommandException;
import exceptions.WarningException;
import interfaces.Commandable;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

public class ExecuteScript extends NamedCommand implements Commandable {
    public final static int MAX_SCRIPT_TRANSITION_COUNT = 100;
    public static int scriptTransitionCount = 0;

    @Override
    public String getInfo() {
        return getName() + " <file_name>\t\t\t\t\t-\tсчитать и исполнить скрипт из указанного файла";
    }

    public String execute(Object inputData, ObjectInputStream in, ObjectOutputStream out, int userId) {
        if (inputData instanceof String scriptName) {
            try {
                File file = new File(scriptName);
                assert file.exists() && file.isFile();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                for (Object line : reader.lines().toArray())
                    try {
                        scriptTransitionCount += 1;
                        if (scriptTransitionCount > MAX_SCRIPT_TRANSITION_COUNT) {
                            scriptTransitionCount = 0;
                            return new WarningException("Произошло зацикливание выполнения скриптов. Программа остановлена").getMessage();
                        }
                        String inputString = (String) line;
                        while (inputString.startsWith(" "))
                            inputString = inputString.substring(1);
                        CommandHandler.handle(inputString, out, userId);
                        String input = in.readUTF();
                        System.out.println(input);
                    } catch (NoSuchCommandException | InvocationTargetException | NoSuchMethodException |
                             InstantiationException | IllegalAccessException e) {
                        return e.getMessage();
                    } catch (IOException e) {
                        System.out.println(TextColor.red("Ошибка соединения"));
                    }
                return "Скрипт " + TextColor.green(scriptName) + " успешно выполнен";
            } catch (AssertionError | FileNotFoundException e) {
                scriptTransitionCount = 0;
                return new DangerException("Файл не найден").getMessage();
            }
        }
        return TextColor.yellow("Неверное количество аргументов. Введите имя файла через пробел");
    }
}
