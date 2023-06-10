package classes;

import classes.abs.NamedCommand;

import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;

public class ReadQueryTask implements Runnable {
    ClientSession clientSession;
    UserCredentials currentUserCredentials;
    ExecutorService outputFixedThreadPool;
    ObjectOutputStream outputStream;

    public ReadQueryTask(ClientSession clientSession,
                         UserCredentials currentUserCredentials,
                         ExecutorService outputFixedThreadPool,
                         ObjectOutputStream outputStream) {
        this.clientSession = clientSession;
        this.currentUserCredentials = currentUserCredentials;
        this.outputFixedThreadPool = outputFixedThreadPool;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        Object inputObject;
        inputObject = clientSession.objectsQueue.pop();
        if (inputObject instanceof UserCredentials receivedCredentials) {
            inputObject = clientSession.objectsQueue.pop();
            if (currentUserCredentials.equals(receivedCredentials))
                if (inputObject instanceof NamedCommand command) {
                    clientSession.commandsQueue.add(command);
                    if (command.hasTransferData())
                        clientSession.commandsQueue.add(clientSession.objectsQueue.pop());
                }
        }
        outputFixedThreadPool.execute(new ExecuteCommandTask(clientSession, outputStream, currentUserCredentials));
    }
}
