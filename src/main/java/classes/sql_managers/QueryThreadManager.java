package classes.sql_managers;

import interfaces.Commandable;

import java.sql.Connection;
import java.util.ArrayList;

public class QueryThreadManager implements Runnable {
    private final Connection connection;
    private final ArrayList<Commandable> commands;


    public QueryThreadManager(Connection connection,ArrayList<Commandable> commands){
        this.connection=connection;
        this.commands=commands;
    }

    @Override
    public void run() {
//        try {
//            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
//            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//            System.out.println(TextColor.grey("Соединение установлено: " + socket.getInetAddress()));
//
//            while (!socket.isClosed()) {
//                try {
//                    Object inputObject = inputStream.readObject();
//                    if (inputObject instanceof NamedCommand command) {
//                        String outputData;
//                        if (command.hasTransferData()) {
//                            Object inputData = inputStream.readObject();
//                            outputData = (String) command.execute(inputData);
//                        } else
//                            outputData = (String) command.execute(null);
//                        if (outputData != null)
//                            outputStream.writeUTF(outputData);
//                    } else throw new ClassNotFoundException();
//                } catch (ClassNotFoundException e) {
//                    outputStream.writeUTF(TextColor.yellow("Передана неизвестная команда"));
//                } finally {
//                    outputStream.flush();
//                }
//            }
//        } catch (IOException e) {
//            System.out.println(TextColor.grey("Соединение разорвано, ожидаю нового подключения"));
//            CollectionManager.saveCollection();
//            System.out.println(TextColor.green("Хранилище сохранено в файл"));
//        }
//    }
    }
}
