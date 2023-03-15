import classes.DataStorage;
import classes.collection.CollectionManager;
import classes.console.CommandHandler;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.xml_manager.XMLMovieManager;
import exceptions.NoSuchCommandException;
import exceptions.SystemException;
import interfaces.Commandable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandHandler commandHandler = new CommandHandler();
        System.out.println(TextColor.purple("Пытаюсь прочитать файл коллекции..."));
        CollectionManager cm = new CollectionManager();
        if (args.length == 1) {
            List<Movie> enteredMovies = XMLMovieManager.getInstance().readCollectionFromXML(args[0]).getMovies();
            if (enteredMovies != null && !enteredMovies.isEmpty()) {
                for (Movie movie : enteredMovies) {
                    cm.addMovie(movie);
                }
                System.out.println(TextColor.purple("Файл коллекции был прочитан..."));
            } else {
                System.out.println(TextColor.purple("Файл коллекции оказался пуст"));
            }
        } else if (args.length == 0) {
            System.out.println(TextColor.purple("Файл коллекции не был указан. Была выбрана коллекция по-умолчанию"));
            DataStorage.setCurrentStorageFilePath(DataStorage.DEFAULT_STORAGE_FILE_PATH);
            List<Movie> enteredMovies = XMLMovieManager.getInstance().readCollectionFromXML().getMovies();
            if (enteredMovies != null && !enteredMovies.isEmpty()) {
                for (Movie movie : enteredMovies) {
                    cm.addMovie(movie);
                }
                System.out.println(TextColor.purple("Файл коллекции был прочитан..."));
            } else {
                System.out.println(TextColor.purple("Файл коллекции оказался пуст"));
            }
        } else {
            System.out.println(TextColor.purple("Провал\nВводите в аргументы программы только одно слово - имя файла с расширением"));
            Runtime.getRuntime().exit(0);
        }
        while (true) {
            System.out.print(TextColor.green("> "));
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
        }
    }
}