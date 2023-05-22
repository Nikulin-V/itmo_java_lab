package classes;

import classes.abs.NamedCommand;
import classes.console.TextColor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

public class ClientSession implements Runnable {
    private final Socket socket;

    public ClientSession(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            System.out.println(TextColor.grey("Соединение установлено: " + socket.getInetAddress()));

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
