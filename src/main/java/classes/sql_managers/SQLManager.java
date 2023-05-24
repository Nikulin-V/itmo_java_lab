package classes.sql_managers;

import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.Person;
import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class SQLManager {
    private final static String createMovieTable = """
            CREATE TABLE IF NOT EXISTS movies(
                uuid_id uuid PRIMARY KEY,
                name VARCHAR(255) NOT NULL CHECK (name <> ''),
                coordinateX BIGINT NOT NULL CHECK (coordinateX <= 279 ),
                coordinateY BIGINT NOT NULL CHECK (coordinateY > -230),
                creation_date DATE NOT NULL,
                oscars_count BIGINT NOT NULL CHECK (oscars_count > 0),
                golden_palm_count BIGINT NOT NULL CHECK (golden_palm_count > 0),
                budget FLOAT NOT NULL CHECK (budget > 0) DEFAULT NULL,
                id_mpaarating INTEGER DEFAULT NULL,
                uuid_director uuid NOT NULL REFERENCES directors(uuid_director) ON DELETE CASCADE,
                user_id VARCHAR(255) NOT NULL REFERENCES users(login) ON DELETE CASCADE
            );""";

    private static final String createDirectorsTable = """
            CREATE TABLE IF NOT EXISTS directors (
                uuid_director uuid PRIMARY KEY,
                name VARCHAR(255) NOT NULL CHECK (name <> ''),
                birthday DATE DEFAULT NULL,
                height DOUBLE PRECISION DEFAULT NULL CHECK (height > 0),
                passport_id VARCHAR(255) NOT NULL UNIQUE CHECK (length(passport_id) >= 7 AND passport_id <> ''),
                eye_color INTEGER NOT NULL
            );""";



    public static void main(String[] args) {
        initDB();
    }

    static Connection getDBConnection() {
        Connection dbConnection = null;
        Dotenv env = Dotenv.load();
        try {
            String url = "jdbc:postgresql://%s:%d/studs".formatted(env.get("DB_HOST"), Integer.parseInt(env.get("DB_PORT")));
            Properties props = new Properties();
            props.setProperty("user", env.get("DB_USER"));
            props.setProperty("password", env.get("DB_PASS"));
            dbConnection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.println(TextColor.green(e.getMessage()));
        } catch (NumberFormatException e) {
            System.out.println(TextColor.red("Переменная окружения DB_PORT должна быть в формате целого числа 0-65535"));
        }
        return dbConnection;
    }

    public static void execute(String command) {
        try (Connection dbConnection = getDBConnection(); Statement statement = dbConnection.createStatement()) {
            statement.execute(command);
            System.out.println(TextColor.cyan("Обращение к базе данных выполнено"));
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к базе данных"));
        }
    }

    public static ResultSet executePreparedQuery(String command, String arg) {
        Connection dbConnection = getDBConnection();
        try  {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(command);
            preparedStatement.setString(1,arg);
            return preparedStatement.executeQuery(command);
        } catch ( NullPointerException | SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к базе данных"));

        }
        return null;
    }

    public static ResultSet executeQuery(String command) {
        Connection dbConnection = getDBConnection();
        try  {
            Statement statement = dbConnection.createStatement();
            return statement.executeQuery(command);
        } catch ( NullPointerException | SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к базе данных"));

        }
        return null;
    }
    public static int executeUpdate(String command) {
        try (Connection dbConnection = getDBConnection(); Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(command);
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к баз данных"));
        }
        return Integer.MAX_VALUE;
    }

    public static int executeMovieDelete(UUID uuid, String userID) {

        String r = """
                DELETE FROM movies WHERE uuid_id=? AND user_id=?
                """;

        try (Connection dbConnection = getDBConnection(); PreparedStatement ps = dbConnection.prepareStatement(r)) {
            int index = 1;
            ps.setObject(index++, uuid);
            ps.setString(index++, userID);
            return ps.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к баз данных"));
        }
        return Integer.MAX_VALUE;
    }

    public static int executeMovieUpdate(UUID uuid, String userID) {

        String r = """
                UPDATE movies
                SET name = ?, coordinatex = ?, coordinatey = ?, oscars_count=? ,golden_palm_count = ?, budget=?, id_mpaarating=?, uuid_director=?
                WHERE uuid_id = ?;
                """;
        try (Connection dbConnection = getDBConnection(); PreparedStatement ps = dbConnection.prepareStatement(r)) {
            int index = 1;

            ps.setString(index++, userID);
            ps.setObject(index++, uuid);
            return ps.executeUpdate();
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к баз данных"));
        }
        return Integer.MAX_VALUE;
    }

    public static boolean insertMovie(Movie movie) throws SQLException{
            Connection connection = getDBConnection();
            insertDirector(movie.getDirector(), movie.getDirector().getDirectorID(), connection);

            String r = "INSERT INTO movies"
                    + "(uuid_id, name, coordinatex, coordinatey, creation_date, oscars_count, golden_palm_count, budget, id_mpaarating, uuid_director, user_id)" + "values"
                    + "(?, ?, ?, ?, ?, ?, ?, ?,?,?,?) ON CONFLICT DO NOTHING";

            try (PreparedStatement preparedStatement = connection.prepareStatement(r)) {
                int index = 1;
                preparedStatement.setObject(index++, movie.getId());
                preparedStatement.setString(index++, movie.getName());
                preparedStatement.setInt(index++, (int) movie.getCoordinates().getX());
                preparedStatement.setInt(index++, (int) movie.getCoordinates().getY());
                preparedStatement.setDate(index++, new java.sql.Date(movie.getCreationDate().getTime()));
                preparedStatement.setFloat(index++, movie.getOscarsCount());
                preparedStatement.setFloat(index++, movie.getGoldenPalmCount());
                preparedStatement.setFloat(index++, movie.getBudget());
                if (movie.getMpaaRating() == null) preparedStatement.setNull(index++, Types.INTEGER);
                else preparedStatement.setInt(index++, movie.getMpaaRating().ordinal() + 1);
                preparedStatement.setObject(index++, movie.getDirector().getDirectorID());
                preparedStatement.setString(index++, movie.getUserID());
                return  preparedStatement.execute();
            } catch (NullPointerException | PSQLException e) {
                e.printStackTrace();
                System.out.println(TextColor.cyan("Ошибка при обращении к базе данных"));
            }
        return false;
    }

    private static boolean insertDirector(Person person, UUID id, Connection connection) {
        String r = "INSERT INTO directors"
                + "(uuid_director, name, birthday, height, passport_id, eye_color)" + "values"
                + "(?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING";

        try (PreparedStatement preparedStatement = connection.prepareStatement(r)) {
            int index = 1;
            preparedStatement.setObject(index++, id);
            preparedStatement.setString(index++, person.getName());
            if (person.getBirthday() == null) preparedStatement.setNull(index++, Types.DATE);
            else preparedStatement.setDate(index++, new java.sql.Date(person.getBirthday().getTime()));
            if (person.getHeight() == null) preparedStatement.setNull(index++, Types.FLOAT);
            else preparedStatement.setDouble(index++, person.getHeight());
            preparedStatement.setString(index++, person.getPassportID());
            preparedStatement.setInt(index++, person.getEyeColor().ordinal() + 1);
            return  preparedStatement.execute();

        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("Ошибка при обращении к базе данных"));
        }
        return false;
    }
    private static void initDB() {
        execute(createDirectorsTable);
        SQLUserManager.init();
        execute(createMovieTable);
    }
}
