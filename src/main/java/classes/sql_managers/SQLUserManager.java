package classes.sql_managers;

import classes.UserCredentials;
import classes.console.TextColor;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUserManager extends SQLManager {
    private final Connection connection = getDBConnection();

    private static final String createUserTableQuery = """
            CREATE TABLE IF NOT EXISTS users(
                username VARCHAR(255) NOT NULL CHECK (username <> '') UNIQUE PRIMARY KEY,
                hashed_password TEXT NOT NULL
            );""";

    public SQLUserManager() {
    }

    public static void init() {
        execute(createUserTableQuery);
    }

    public boolean addUser(UserCredentials credentials) {
        String addUserQuery = "INSERT INTO users (username, hashed_password) VALUES (?, ?) ON CONFLICT DO NOTHING";
        try (PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery)) {
            int index = 1;
            preparedStatement.setString(index++, credentials.getUsername());
            preparedStatement.setString(index++, credentials.getHashedPassword());
            return preparedStatement.execute();
        } catch (NullPointerException | PSQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("Ошибка при обращении к базе данных!"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
