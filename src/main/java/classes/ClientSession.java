package classes;

import classes.abs.NamedCommand;
import classes.console.TextColor;
import classes.sql_managers.SQLManager;
import classes.sql_managers.SQLUserManager;

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

    private Response executeCommand(ObjectInputStream inputStream,
                                    UserCredentials userCredentials) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object inputObject = inputStream.readObject();
        if (inputObject instanceof NamedCommand command) {
            if (command.hasTransferData()) {
                Object inputData = inputStream.readObject();
                return command.execute(inputData, userCredentials.getUsername());
            } else return command.execute(null, userCredentials.getUsername());
        }
        return new Response(1, TextColor.yellow("Передана неизвестная команда"));
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
                        outputStream.writeObject(executeCommand(inputStream, currentUserCredentials));
                    } else
                        outputStream.writeObject(new Response(1, TextColor.red("Попытка подмены данных пользователя")));
                    outputStream.flush();
                }
            }
        } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                 InstantiationException | IllegalAccessException e) {
            System.out.println(TextColor.grey("Соединение разорвано, ожидаю нового подключения"));
        }
    }

    private UserCredentials authorize(ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        while (true) {
            UserCredentials userCredentials = (UserCredentials) in.readObject();
            Response outputData;
            if (userCredentials.isRegistration())
                outputData = register(userCredentials);
            else outputData = login(userCredentials);
            out.writeObject(outputData);
            out.flush();
            if (outputData.getCode() == 0)
                return userCredentials;
        }
    }

    private Response login(UserCredentials userCredentials) {
        if (isLoginInDB(userCredentials.getUsername()))
            if (checkPassword(userCredentials))
                return new Response(0, TextColor.green("Авторизация прошла успешно"));
            else return new Response(1, TextColor.red("Неверный пароль\n"));
        return new Response(1, TextColor.yellow("Пользователь с таким логином не существует\n"));
    }

    private Response register(UserCredentials userCredentials) {
        if (!isLoginInDB(userCredentials.getUsername()))
            if (new SQLUserManager().addUser(userCredentials))
                return new Response(0, TextColor.green("Аккаунт зарегистрирован\n"));
            else return new Response(1, TextColor.red("Ошибка регистрации\n"));
        return new Response(1, TextColor.yellow("Пользователь с таким логином уже существует\n"));
    }
}
