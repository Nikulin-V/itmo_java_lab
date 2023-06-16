package classes;

import classes.abs.NamedCommand;
import classes.console.TextColor;
import classes.sql_managers.SQLManager;
import classes.sql_managers.SQLUserManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientSession implements Runnable {
    private final Socket socket;
    public final static ExecutorService outputFixedThreadPool = Executors.newFixedThreadPool(10);
    public final static ExecutorService inputCachedThreadPool = Executors.newCachedThreadPool();
    public final ArrayDeque<Object> objectsQueue = new ArrayDeque<>();
    public final ArrayDeque<Object> commandsQueue = new ArrayDeque<>();

    public ClientSession(Socket socket) {
        this.socket = socket;
    }

    private boolean checkPassword(UserCredentials userCredentials) {
        String password = SQLManager.executePreparedQueryGetPassword(userCredentials.getUsername());
        if (password != null)
            return password.equals(userCredentials.getHashedPassword());
        return false;
    }

    private boolean isLoginInDB(String username) {
        return SQLManager.executeCheckLogin(username);
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
                readFullRequest(inputStream);
                inputCachedThreadPool.execute(new ReadQueryTask(this, currentUserCredentials, outputFixedThreadPool, outputStream));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(TextColor.grey("Соединение разорвано: " + socket.getInetAddress()));
        }
    }

    private void readFullRequest(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object credentials;
        Object inputObject;
        credentials = inputStream.readObject();
        if (credentials instanceof UserCredentials) {
            inputObject = inputStream.readObject();
            if (inputObject instanceof NamedCommand command) {
                objectsQueue.add(credentials);
                objectsQueue.add(command);
                if (command.hasTransferData())
                    objectsQueue.add(inputStream.readObject());
            }
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
                return new Response(0, "Авторизация прошла успешно");
            else return new Response(1, "Неверный пароль");
        return new Response(1, "Пользователя с таким логином не существует");
    }

    private Response register(UserCredentials userCredentials) {
        if (!isLoginInDB(userCredentials.getUsername()))
            if (new SQLUserManager().addUser(userCredentials))
                return new Response(0, "Аккаунт зарегистрирован");
            else return new Response(1, "Ошибка регистрации");
        return new Response(1, "Пользователь с таким логином уже существует");
    }
}