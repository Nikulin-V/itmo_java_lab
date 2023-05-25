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

    }
}
