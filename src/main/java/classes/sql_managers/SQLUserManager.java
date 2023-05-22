package classes.sql_managers;

import classes.console.TextColor;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLUserManager extends SQLManager {
    private final Connection connection = getDBConnection();

    private static final String createUserTableQuery = """
            CREATE TABLE IF NOT EXISTS users(
                id uuid PRIMARY KEY,
                login VARCHAR(255) NOT NULL CHECK (login <> '') UNIQUE,
                hashed_password TEXT NOT NULL
            );""";

    public SQLUserManager() {}

    public void init() {
        execute(createUserTableQuery);
    }

    public void getUserFromSQL(String login) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE login = ?",
                    ResultSet.TYPE_FORWARD_ONLY
            );
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("user_id");
                String userLogin = rs.getString("login");
                String passHash = rs.getString("pass_hash");
                char[] passSalt = rs.getString("pass_salt").toCharArray();

                //check password here ...
                String userPassHash = null;
                if (userPassHash.equals(passHash)){
                    rs.updateRow();
                    rs.close();
                    statement.close();
                }
            } else{
                rs.close();
                statement.close();
                System.out.println(TextColor.red("Неверный пароль для этого пользователя"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertUser(String name, String hash, String salt) {
        String r = "INSERT INTO users (uuid_user, login, pass_hash, pass_salt) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING";
        try (PreparedStatement preparedStatement = connection.prepareStatement(r)) {
            int index = 1;
            preparedStatement.setObject(index++, UUID.randomUUID());
            preparedStatement.setString(index++, name); // TODO VALIDATORS here?
            preparedStatement.setString(index++, hash); // TODO VALIDATORS here?
            preparedStatement.setString(index++, salt); // TODO VALIDATORS here?
            preparedStatement.execute();
        } catch (NullPointerException | PSQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("Ошибка при обращении к базе данных!"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(String login) {}
}
