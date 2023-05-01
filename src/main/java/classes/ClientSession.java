package classes;

import classes.abs.NamedCommand;
import classes.collection.CollectionManager;
import classes.console.TextColor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                try {
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
                    } else throw new ClassNotFoundException();
                } catch (ClassNotFoundException e) {
                    outputStream.writeObject(new Response(1).setData(TextColor.yellow("Передана неизвестная команда")));
                } finally {
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            System.out.println(TextColor.grey("Соединение разорвано, ожидаю нового подключения"));
            CollectionManager.saveCollection();
            System.out.println(TextColor.green("Хранилище сохранено в файл"));
        }
    }
}