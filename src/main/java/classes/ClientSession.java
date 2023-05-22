package classes;

import classes.abs.NamedCommand;
import classes.console.TextColor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.UUID;

public class ClientSession implements Runnable {
    private final Socket socket;

    public ClientSession(Socket socket) {
        this.socket = socket;
    }


    private UUID authorize(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        String userChoice = "";
        while (!(userChoice.equals("1") || userChoice.equals("2"))) {
            out.writeUTF("1 - Войти (по умолчанию)\n2 - Зарегистрироваться");
            out.flush();
            userChoice = in.readUTF();
        }
        if (userChoice.equals("2"))
            return register(in, out);
        else return login(in, out);
    }

    private UUID login(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        while (true) {
            out.writeUTF("Логин: ");
            out.flush();
            String login = in.readUTF();
            out.writeUTF("Пароль: ");
            out.flush();
            String password = in.readUTF();

            if (checkLogin(login)) {
                UUID userID = checkPassword(password);
                if (userID != null)
                    return userID;
                else System.out.println(TextColor.red("Неверный пароль\n"));
            } else System.out.println(TextColor.yellow("Пользователь с таким логином не существует\n"));
        }
    }

    private UUID checkPassword(String password) {
        UUID userID;
        return userID;
    }

    private boolean checkLogin(String login) {
        return false;
    }

    private UUID register(ObjectInputStream in, ObjectOutputStream out) {
        // TODO: write user registration code
        UUID userID;
        return userID;
    }

    @Override
    public void run() {
        try (
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            System.out.println(TextColor.grey("Соединение установлено: " + socket.getInetAddress()));
            UUID currentUserID = authorize(inputStream, outputStream);

            while (!socket.isClosed()) {
                Object inputObject = inputStream.readObject();
                if (inputObject instanceof NamedCommand command) {
                    Response outputData;
                    if (command.hasTransferData()) {
                        Object inputData = inputStream.readObject();
                        outputData =  command.execute(inputData);
                    } else
                        outputData = command.execute(null);
                    if (outputData != null)
                        outputStream.writeObject(outputData);
                } else outputStream.writeObject(new Response(1).setData(TextColor.yellow("Передана неизвестная команда")));
                outputStream.flush();
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            System.out.println(TextColor.grey("Соединение разорвано, ожидаю нового подключения"));
        }
    }
}
