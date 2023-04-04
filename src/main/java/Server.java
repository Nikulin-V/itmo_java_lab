import classes.collection.CollectionManager;
import classes.commands.Exit;
import classes.console.CommandHandler;
import classes.console.TextColor;
import exceptions.NoSuchCommandException;
import interfaces.Commandable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Server {
    public static void main(String[] args) {
        if (args == null || args.length == 0 || args.length >= 3) {
            System.out.println(TextColor.red("Слишком много аргументов"));
            System.out.println(TextColor.red("При запуске программы введите в аргументах номер порта (0-65535) и имя файла коллекции"));
            new Exit().execute();
        }

        String fileName = args[0];
        CollectionManager.readFile(fileName);

        int port = 14600;

        if (args.length <= 2)
            try {
                port = Integer.parseInt(args[0]);
                if (0 > port || port > 65535)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println(TextColor.red("Неправильный формат ввода порта\nномер порта должен быть в диапазоне от 0 до 65535"));
                new Exit().execute();
            }
        System.out.println(TextColor.green("Сервер запущен на " + port + " порту"));
        System.out.println(TextColor.grey("Ожидание соединения..."));
        start(port);
    }

    private static void start(int port) {
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                Socket client = server.accept();
                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());
                System.out.println(TextColor.grey("Соединение установлено: " + client.getInetAddress()));
                CommandHandler commandHandler = new CommandHandler();

                while (!client.isClosed()) {
                    try {
                        String inputString = inputStream.readUTF();
                        String commandName = inputString.split(" ")[0];
                        String[] commandArguments = null;
                        if (inputString.split(" ").length > 1) {
                            String[] arr = inputString.split(" ");
                            commandArguments = Arrays.copyOfRange(arr, 1, arr.length);
                        }
                        Commandable command = commandHandler.getCommand(commandName);
                        if (!command.getName().equals("exit")) {
                            outputStream.writeUTF(command.execute(commandArguments));
                            outputStream.flush();
                        }
                    } catch (InvocationTargetException | NoSuchMethodException |
                             InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchCommandException e) {
                        outputStream.writeUTF(TextColor.yellow(e.getMessage()));
                        outputStream.flush();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(TextColor.grey("Соединение разорвано"));
            start(port);
        }
    }
}