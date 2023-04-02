import classes.commands.Exit;
import classes.console.TextColor;
import exceptions.WarningException;
import interfaces.Commandable;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        if (args.length >= 2) {
            System.out.println(TextColor.red("Слишком много аргументов"));
            System.out.println(TextColor.red("При запуске программы введите в аргументах номер порта (0-65535)"));
        }

        int port = 14600;
        if (args.length == 1)
            try {
                port = Integer.parseInt(args[0]);
                if (0 > port || port > 65535)
                    throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.out.println(TextColor.red("Номер порта должен быть в диапазоне от 0 до 65535"));
                new Exit().execute();
            }

        try (ServerSocket server = new ServerSocket(port)) {
            Socket client = server.accept();
            System.out.println(TextColor.grey("Connection accepted: " + client.getInetAddress()));
            DataInputStream in = new DataInputStream(client.getInputStream());
            DataOutputStream out = new DataOutputStream(client.getOutputStream());

            while (!client.isClosed()) {
                ObjectInputStream inputStream = new ObjectInputStream(in);
                try {
                    Commandable command = (Commandable) inputStream.readObject();
                    command.execute();
                    out.flush();
                } catch (ClassNotFoundException e) {
                    new WarningException("Команда не распознана:\n" + inputStream).printMessage();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}