import classes.abs.NamedCommand;
import classes.commands.Exit;
import classes.console.CommandHandler;
import classes.console.InputHandler;
import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.RandomMovie;
import exceptions.NoSuchCommandException;
import exceptions.SystemException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Client {
    private static int connectAttemptsCount = 0;
    private static final int maxConnectAttemptsCount = 15;
    private static final int timeoutMilliseconds = 1000;
    private static String lastRequest;

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println(TextColor.red("Неверное число аргументов"));
            System.out.println(TextColor.red("При запуске программы введите в аргументах имя хоста и номер порта (0-65535)"));
            new Exit().execute(null);
        }

        String host = args[0];
        int port = 14600;
        try {
            port = Integer.parseInt(args[1]);
            if (0 > port || port > 65535)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println(TextColor.red("Номер порта должен быть в диапазоне от 0 до 65535"));
            new Exit().execute(null);
        }
        connect(host, port);
    }

    private static void connect(String host, int port) throws InterruptedException {
        System.out.println(TextColor.grey("Пытаюсь установить соединение с сервером..."));
        try (Socket socket = new Socket(host, port)) {
            System.out.println(TextColor.green("Соединение установлено"));
            connectAttemptsCount = 0;
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            CommandHandler commandHandler = new CommandHandler();
            if (lastRequest == null)
                System.out.print(TextColor.green("> "));

            while (lastRequest != null || scanner.hasNextLine()) {
                try {
                    String inputString = lastRequest == null ? scanner.nextLine() : lastRequest;
                    lastRequest = inputString;
                    String commandName = inputString.split(" ")[0];
                    String[] commandArguments = null;
                    if (inputString.split(" ").length > 1) {
                        String[] arr = inputString.split(" ");
                        commandArguments = Arrays.copyOfRange(arr, 1, arr.length);
                    }
                    if (!commandName.isBlank()) {
                        NamedCommand command = commandHandler.getCommand(commandName);
                        if (Objects.equals(command.getName(), "exit"))
                            command.execute(null);
                        if (Objects.equals(command.getName(), "execute_script")) {
                            // TODO: execute_script
                        }
                        if (command.isNeedInput() && commandArguments != null) {
                            if (Objects.equals(command.getName(), "add")) {
                                Movie movie = null;
                                if (commandArguments.length == 1 && commandArguments[0].equals("random")) {
                                    //TODO transfer executing random movie generating to server side
                                    movie = RandomMovie.generate();
                                }
                                out.writeObject(command);
                                out.flush();
                                out.writeObject(movie);
                            } else if (Objects.equals(command.getName(), "update")) {
                                Movie movie;
                                InputHandler inputHandler = new InputHandler();
                                movie = inputHandler.readMovie();
                                movie.setId(UUID.fromString(commandArguments[0]));
                                out.writeObject(command);
                                out.flush();
                                out.writeObject(movie);
                                // remove by id = просто отправить id
                                // count_by_oscars_count - отправить циферку для вывода фильмов с таким-то кол-вом оскаров
                                // remove_lower - X, Y coords
                                // count greater than director - сравниваем строчки

                            }
                        } else if
                                (Objects.equals(command.getName(), "remove_by_id")
                                        || Objects.equals(command.getName(), "remove_lower")
                                        || Objects.equals(command.getName(), "count_by_oscars_count")
                                        || Objects.equals(command.getName(), "count_greater_than_director")) {
                            out.writeObject(command);
                            out.flush();
                            out.writeObject(commandArguments);
                        } else out.writeObject(command);
                        out.flush();
                    }
                    String input = in.readUTF();
                    System.out.println(input);
                    lastRequest = null;
                } catch (NoSuchCommandException e) {
                    e.printMessage();
                    lastRequest = null;
                } catch (NoSuchMethodException | InvocationTargetException |
                         InstantiationException | IllegalAccessException e) {
                    new SystemException().printMessage();
                }
                System.out.print(TextColor.green("> "));
            }
        } catch (UnknownHostException e) {
            System.out.println(TextColor.red("Неизвестный хост: " + host));
        } catch (SocketException e) {
            if (connectAttemptsCount == 0)
                System.out.println(TextColor.yellow("Сервер не отвечает"));
            System.out.println(TextColor.grey("Пытаюсь установить соединение с сервером..."));
            if (connectAttemptsCount <= maxConnectAttemptsCount) {
                connectAttemptsCount += 1;
                Thread.sleep(timeoutMilliseconds);
                connect(host, port);
            } else System.out.println(TextColor.red("Не удаётся установить соединение"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}