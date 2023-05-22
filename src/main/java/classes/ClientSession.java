package classes;

import classes.abs.NamedCommand;
import classes.console.TextColor;
import classes.sql_managers.SQLManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientSession implements Runnable {
    private final Socket socket;

    public ClientSession(Socket socket) {
        this.socket = socket;
    }

    private UserCredentials authorize(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        String userChoice = in.readUTF();
        while (!(userChoice.equals("1") || userChoice.equals("2"))) {
            userChoice = in.readUTF();
        }
        if (userChoice.equals("2"))
            return register(in, out);
        else return login(in, out);
    }

    private UserCredentials login(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        while (true) {
            out.writeUTF("Логин: ");
            out.flush();
            String username = in.readUTF();
            out.writeUTF("Пароль: ");
            out.flush();
            String password = in.readUTF();

            if (isLoginInDB(username)) {
                UserCredentials userCredentials = new UserCredentials(username, password);
                if (checkPassword(userCredentials)) {
                    return userCredentials;
                } else System.out.println(TextColor.red("Неверный пароль\n"));
            } else System.out.println(TextColor.yellow("Пользователь с таким логином не существует\n"));
        }
    }

    private boolean checkPassword(UserCredentials userCredentials) {
        ResultSet loginResultSet = SQLManager.executeQuery("SELECT * FROM users where login=" + userCredentials.getUsername());
        try {
            if (loginResultSet != null) {
                String hashedPassword = loginResultSet.getString("pass_hash");
                return hashedPassword.equals(userCredentials.getHashedPassword());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private boolean isLoginInDB(String login) {
        ResultSet loginResultSet = SQLManager.executeQuery("SELECT * FROM users WHERE login=" + login);
        return loginResultSet != null;
    }

    private UserCredentials register(ObjectInputStream in, ObjectOutputStream out) throws IOException {
        String username = null, password;
        while (isLoginInDB(username)) {
            if (username != null)
                System.out.println(TextColor.yellow("Пользователь с таким логином уже существует\n"));
            out.writeUTF("Логин: ");
            out.flush();
            username = in.readUTF();
        }
        out.writeUTF("Пароль: ");
        out.flush();
        password = in.readUTF();
        return new UserCredentials(username, password);
    }

    @Override
    public void run() {
        try (
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            System.out.println(TextColor.grey("Соединение установлено: " + socket.getInetAddress()));
            UserCredentials currentUserCredentials = authorize(inputStream, outputStream);

            while (!socket.isClosed()) {
                Object inputObject = inputStream.readObject();
                if (inputObject instanceof UserCredentials credentials) {
                    if (currentUserCredentials.equals(credentials)) {
                        inputObject = inputStream.readObject();
                        if (inputObject instanceof NamedCommand command) {
                            Response outputData;
                            if (command.hasTransferData()) {
                                Object inputData = inputStream.readObject();
                                outputData = command.execute(inputData, currentUserCredentials.getUsername());
                            } else
                                outputData = command.execute(null, currentUserCredentials.getUsername());
                            if (outputData != null)
                                outputStream.writeObject(outputData);
                        } else
                            outputStream.writeObject(new Response(1).setData(TextColor.yellow("Передана неизвестная команда")));
                    } else
                        outputStream.writeObject(new Response(1).setData(TextColor.red("Попытка подмены данных пользователя")));
                    outputStream.flush();
                }
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            System.out.println(TextColor.grey("Соединение разорвано, ожидаю нового подключения"));
        }
    }
}
