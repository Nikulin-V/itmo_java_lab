package classes;

import classes.console.CommandHandler;
import classes.console.TextColor;
import exceptions.NoSuchCommandException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

public class ExceptionCommandHandler {
    public static Response handleExceptions(String inputString, ObjectOutputStream out, UserCredentials credentials) {
        try {
            CommandHandler.handle(inputString, out, credentials);
        } catch (NoSuchCommandException e) {
            return new Response(1, TextColor.red("Неизвестная команда"));
        } catch (InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            return new Response(1, TextColor.red("Произошла фатальная ошибка считывания команд из файла"));
        } catch (IllegalArgumentException e) {
            return new Response(1, TextColor.yellow("Неверный формат ввода.\nПроверьте отсутствие лишних пробелов и правильный формат данных"));
        } catch (IndexOutOfBoundsException e) {
            return new Response(1, TextColor.yellow(TextColor.yellow("Введенный индекс выходит за пределы размера нынешней коллекции")));
        } catch (AssertionError | IOException e) {
            return new Response(1, TextColor.red("Ошибка чтения файла, возможно файл не найден"));
        }
        return null;
    }
}


