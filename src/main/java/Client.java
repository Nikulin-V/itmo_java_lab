import classes.Response;
import classes.commands.ExecuteScript;
import classes.commands.Exit;
import classes.console.CommandHandler;
import classes.console.TextColor;
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
import java.util.Scanner;

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
            if (lastRequest == null)
                System.out.print(TextColor.green("> "));

            while (lastRequest != null || scanner.hasNextLine()) {
                try {
                    String inputString = lastRequest == null ? scanner.nextLine() : lastRequest;
                    if (!inputString.isBlank()) {
                        lastRequest = inputString;
                        Response input = Response.getEmptyResponce();
                        if (!inputString.startsWith("execute_script")) {
                            CommandHandler.handle(inputString, out);
                            input = (Response) in.readObject();
                        } else {
                            String[] commandArguments = null;
                            if (inputString.split(" ").length > 1) {
                                String[] arr = inputString.split(" ");
                                commandArguments = Arrays.copyOfRange(arr, 1, arr.length);
                            }
                            if (commandArguments != null && commandArguments.length == 1)
                                input = new ExecuteScript().execute(commandArguments[0], in, out);
                        }
                        System.out.println(input.getData());
                        lastRequest = null;
                    }
                } catch (NoSuchCommandException e) {
                    e.printMessage();
                    lastRequest = null;
                } catch (NoSuchMethodException | InvocationTargetException |
                         InstantiationException | IllegalAccessException e) {
                    new SystemException().printMessage();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
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
            System.out.println(TextColor.red("Ошибка соединения"));
        }
    }
}