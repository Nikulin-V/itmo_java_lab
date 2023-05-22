import classes.ClientSession;
import classes.collection.CollectionManager;
import classes.commands.Exit;
import classes.console.TextColor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
            CollectionManager.readDB();
            System.out.println(TextColor.green("Сервер запущен на " + port + " порту"));
            System.out.println(TextColor.grey("Ожидание соединения..."));
            while (true) {
                try (ServerSocket server = new ServerSocket(port)) {
                    server.setReuseAddress(true);
                    Socket clientSocket = server.accept();
                    ClientSession clientSession = new ClientSession(clientSocket);
                    Thread clientSessionThread = new Thread(clientSession);
                    clientSessionThread.start();
                } catch (IOException e) {
                    System.out.println(TextColor.grey("Соединение разорвано, ожидаю нового подключения"));
                    CollectionManager.saveCollection();
                    System.out.println(TextColor.green("Хранилище сохранено в файл"));
                }
            }
        }
    }
}