import classes.collection.CollectionManager;
import classes.commands.Exit;
import classes.commands.Save;
import classes.console.CommandHandler;
import classes.console.TextColor;
import interfaces.Commandable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Objects;

public class Server {
    public static void main(String[] args) {
        if (args == null || args.length == 0 || args.length >= 3) {
            System.out.println(TextColor.red("Неверное число аргументов"));
            System.out.println(TextColor.red("При запуске программы введите в аргументах номер порта (0-65535) и имя файла коллекции"));
            new Exit().execute(null);
        } else {
            int port = 14600;
            try {
                port = Integer.parseInt(args[0]);
                if (0 > port || port > 65535)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println(TextColor.red("Неправильный формат ввода порта\nномер порта должен быть в диапазоне от 0 до 65535"));
                new Exit().execute(null);
            }
            String fileName = args.length == 2 ? args[1]: null;
            CollectionManager.readFile(fileName);
            System.out.println(TextColor.green("Сервер запущен на " + port + " порту"));
            System.out.println(TextColor.grey("Ожидание соединения..."));
            start(port);
        }
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
                        Object inputObject = inputStream.readObject();
                        if (inputObject instanceof Commandable command) {
                            if (command.hasTransferData()) {
                                Object inputData = inputStream.readObject();
                                outputStream.writeObject(command.execute(inputData));
                            } else {
                                outputStream.writeObject(command.execute(null));
                                outputStream.flush();
                            }
                        } else throw new ClassNotFoundException();
                    } catch (ClassNotFoundException e) {
                        outputStream.writeUTF(TextColor.yellow("Передана неизвестная команда"));
                        outputStream.flush();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(TextColor.grey("Соединение разорвано, ожидаю нового подключения"));
            new Save().execute(null);
            System.out.println(TextColor.green("Хранилище сохранено в файл"));
            start(port);
        }
    }
}