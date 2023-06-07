package classes.sql_managers;

import interfaces.Commandable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueryThreadManager{
    private final Connection connection;
    private final ArrayList<Commandable> commands;

    private final static ExecutorService outputFixedThreadPool = Executors.newFixedThreadPool(50);

    public QueryThreadManager(Connection connection, ArrayList<Commandable> commands) {
        this.connection = connection;
        this.commands = commands;
    }


    public static void run() {
//
//        outputFixedThreadPool.execute(() -> {
//            try {
//                if (currentUserCredentials.equals(credentials)) {
//                    outputStream.writeObject(executeCommand(inputStream, currentUserCredentials));
//                } else
//                    outputStream.writeObject(new Response(1, TextColor.red("Попытка подмены данных пользователя")));
//                outputStream.flush();
//            } catch (IOException | ClassNotFoundException | InvocationTargetException |
//                     NoSuchMethodException | InstantiationException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//        });


    }
}
