package classes.commands;

import classes.Response;
import classes.abs.NamedCommand;
import classes.console.CommandHandler;
import classes.console.TextColor;
import exceptions.NoSuchCommandException;
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

    public Response execute(Object inputData, ObjectInputStream in, ObjectOutputStream out) {
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
                            return new Response(1).setData(TextColor.yellow("Произошло зацикливание " +
                                    "выполнения скриптов. Программа остановлена"));
                        }
                        String inputString = (String) line;
                        while (inputString.startsWith(" "))
                            inputString = inputString.substring(1);
                        CommandHandler.handle(inputString, out);
                        String input = in.readUTF();
                        System.out.println(input);
                    } catch (NoSuchCommandException | InvocationTargetException | NoSuchMethodException |
                             InstantiationException | IllegalAccessException e) {
                        return new Response(1).setData(TextColor.red("Произошла ошибка считывания " +
                                "команд из файла"));
                    } catch (IOException e) {
                        return new Response(1).setData(TextColor.yellow("Ошибка соединения"));
                    }
                return new Response(0).setData("Скрипт " + TextColor.green(scriptName) + " успешно выполнен");
            } catch (AssertionError | FileNotFoundException e) {
                scriptTransitionCount = 0;
                return new Response(1).setData(TextColor.red("Файл не найден"));
            }
        }
        return new Response(1).setData(TextColor.yellow("Неверное количество аргументов. Введите имя файла через пробел"));
    }
}
