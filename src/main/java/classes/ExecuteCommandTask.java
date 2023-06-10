package classes;

import classes.abs.NamedCommand;
import classes.console.TextColor;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;

public class ExecuteCommandTask implements Runnable {
    ClientSession clientSession;
    ObjectOutputStream outputStream;
    UserCredentials currentUserCredentials;

    ExecuteCommandTask(ClientSession clientSession,
                       ObjectOutputStream outputStream,
                       UserCredentials currentUserCredentials) {
        this.clientSession = clientSession;
        this.outputStream = outputStream;
        this.currentUserCredentials = currentUserCredentials;
    }

    @Override
    public void run() {
        try {
            outputStream.writeObject(executeCommand(currentUserCredentials));
            outputStream.flush();
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            System.out.println(TextColor.red("Ошибка выполнения команды"));
        }
    }

    private Response executeCommand(UserCredentials userCredentials) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Object inputObject = clientSession.commandsQueue.pop();
        if (inputObject instanceof NamedCommand command) {
            if (command.hasTransferData()) {
                Object inputData = clientSession.commandsQueue.pop();
                return command.execute(inputData, userCredentials.getUsername());
            } else return command.execute(null, userCredentials.getUsername());
        }
        return new Response(1, TextColor.yellow("Передана неизвестная команда"));
    }
}
