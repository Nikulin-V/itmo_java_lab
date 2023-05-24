import classes.ExceptionCommandHandler;
import classes.Response;
import classes.UserCredentials;
import classes.commands.ExecuteScript;
import classes.console.CommandHandler;
import classes.console.InputHandler;
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
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println(TextColor.red("Неверное число аргументов"));
            System.out.println(TextColor.red("При запуске программы введите в аргументах имя хоста и номер порта (0-65535)"));
            System.out.println("Завершение работы...");
            Runtime.getRuntime().exit(0);
        }

        String host = args[0];
        int port = 14600;
        try {
            port = Integer.parseInt(args[1]);
            if (0 > port || port > 65535)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            System.out.println(TextColor.red("Номер порта должен быть в диапазоне от 0 до 65535"));
            System.out.println("Завершение работы...");
            Runtime.getRuntime().exit(0);
        }
        connect(host, port);
    }

    private static UserCredentials authorize(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        while (true) {
            String choice = InputHandler.readLoginRegisterChoice();
            UserCredentials credentials = InputHandler.readCredentials();
            if (choice.equals("2"))
                credentials.setRegistration(true);
            out.writeObject(credentials);
            out.flush();
            try {
                Response response = (Response) in.readObject();

                if (response.getCode() == 0)
                    return credentials;
                else System.out.println(response.getData());
            } catch (ClassNotFoundException e) {
                System.out.println(TextColor.yellow("Ошибка авторизации"));
            }
        }
    }

    private static void connect(String host, int port) throws InterruptedException {
        System.out.println(TextColor.grey("Пытаюсь установить соединение с сервером..."));
        try (Socket socket = new Socket(host, port)) {
            System.out.println(TextColor.green("Соединение установлено"));
            connectAttemptsCount = 0;
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            UserCredentials credentials = authorize(in, out);

            if (CommandHandler.getLastRequest() == null)
                System.out.print(TextColor.green("> "));

            while (CommandHandler.getLastRequest() != null || scanner.hasNextLine()) {
                sendCommand(in, out, credentials);
                System.out.print(TextColor.green("> "));
            }
        } catch (UnknownHostException e) {
            System.out.println(TextColor.red("Неизвестный хост: " + host));
        } catch (SocketException e) {
            if (connectAttemptsCount == 0)
                System.out.println(TextColor.yellow("Сервер не отвечает"));
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

    private static void sendCommand(ObjectInputStream in, ObjectOutputStream out, UserCredentials credentials) throws IOException {
        try {
            String inputString;
            if (hasLastRequest()) {
                System.out.println(TextColor.green("Выполняю последний запрос (" + CommandHandler.getLastRequest() + ")"));
                inputString = CommandHandler.getLastRequest();
            } else inputString = scanner.nextLine();

            while (inputString != null && inputString.startsWith(" "))
                inputString = inputString.substring(1);
            if (!(inputString == null || inputString.isBlank())) {
                CommandHandler.setLastRequest(inputString);
                Response response = new Response(0);
                if (!inputString.startsWith("execute_script")) {
                    response = ExceptionCommandHandler.handleExceptions(inputString, out, credentials);
                    if (response == null)
                        response = (Response) in.readObject();
                } else {
                    String[] commandArguments = null;
                    if (inputString.split(" ").length > 1) {
                        String[] arr = inputString.split(" ");
                        commandArguments = Arrays.copyOfRange(arr, 1, arr.length);
                    }
                    if (commandArguments != null && commandArguments.length == 1)
                        response = new ExecuteScript().execute(commandArguments[0], in, out, credentials);
                }
                System.out.println(response.getData());
                CommandHandler.clearLastRequest();
            }
        } catch (NoSuchCommandException e) {
            e.printMessage();
            CommandHandler.clearLastRequest();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | ClassNotFoundException e) {
            new SystemException().printMessage();
        }
    }

    private static boolean hasLastRequest() {
        return CommandHandler.getLastRequest() != null && needLastRequest();
    }

    private static boolean needLastRequest() {
        System.out.println(TextColor.yellow("Хотите повторить последний запрос?\nПоследний запрос: ") +
                TextColor.green(CommandHandler.getLastRequest() + "\n") +
                TextColor.yellow("1 - да (по умолчанию)\n2 - нет"));
        System.out.print(TextColor.green("> "));

        String inputString = null;
        while (inputString == null || !(inputString.equals("") || inputString.equals("1") || inputString.equals("2"))) {
            inputString = scanner.nextLine();
        }
        boolean isUserNeedLastRequest = inputString.equals("") || inputString.equals("1");

        if (!isUserNeedLastRequest) {
            CommandHandler.clearLastRequest();
            System.out.print(TextColor.green("> "));
        }
        return isUserNeedLastRequest;
    }
}