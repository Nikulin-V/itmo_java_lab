package classes.sql_managers;

import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.Person;
import io.github.cdimascio.dotenv.Dotenv;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к базе данных"));
        }
    }

    public static ResultSet executePreparedQueryUUID(String command, UUID arg) {
        Connection dbConnection = getDBConnection();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(command);
            preparedStatement.setObject(1, arg);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к базе данных"));
        }
        return null;
    }

    public static ResultSet executePreparedQuery(String command, String arg) {
        Connection dbConnection = getDBConnection();
        try {
            PreparedStatement preparedStatement = dbConnection.prepareStatement(command);
            preparedStatement.setString(1, arg);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к базе данных"));

        }
        return null;
    }

    public static ResultSet executeQuery(String command) {
        Connection dbConnection = getDBConnection();
        try {
            Statement statement = dbConnection.createStatement();
            return statement.executeQuery(command);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к базе данных"));

        }
        return null;
    }

    public static int executeUpdate(String command) {
        try (Connection dbConnection = getDBConnection(); Statement statement = dbConnection.createStatement()) {
            return statement.executeUpdate(command);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к баз данных"));
        }
        return Integer.MAX_VALUE;
    }

    public static void executeMovieDelete(UUID uuid, String userID) {

        String r = """
                DELETE FROM movies WHERE uuid_id=? AND user_id=?
                """;

        try (Connection dbConnection = getDBConnection(); PreparedStatement ps = dbConnection.prepareStatement(r)) {
            AtomicInteger index = new AtomicInteger(0);
            ps.setObject(index.incrementAndGet(), uuid);
            ps.setString(index.get(), userID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к баз данных"));
        }

    }
//deleted argument user id
    public static int executeMovieUpdate(Movie movie) {

        String movieQuery = """
                UPDATE movies
                SET name=?, coordinatex=?, coordinatey=?, oscars_count=?, golden_palm_count=?, budget=?, id_mpaarating=?, uuid_director=?
                WHERE uuid_id = ?;
                """;

        String directorQuery = """
                INSERT INTO directors
                (uuid_director, name, birthday, height, passport_id, eye_color) VALUES
                (?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING;
                """;
        try (Connection dbConnection = getDBConnection();
             PreparedStatement psMovie = dbConnection.prepareStatement(movieQuery);
             PreparedStatement psDirector = dbConnection.prepareStatement(directorQuery)) {

            AtomicInteger directorIndex = new AtomicInteger(0);
            Person director = movie.getDirector();
            movie.getDirector().setID(UUID.randomUUID());
            psDirector.setObject(directorIndex.incrementAndGet(), director.getID());
            psDirector.setString(directorIndex.incrementAndGet(), director.getName());
            psDirector.setDate(directorIndex.incrementAndGet(), new java.sql.Date(director.getBirthday().getTime()));
            psDirector.setDouble(directorIndex.incrementAndGet(), director.getHeight());
            psDirector.setString(directorIndex.incrementAndGet(), director.getPassportID());
            psDirector.setInt(directorIndex.get(), director.getEyeColor().ordinal() + 1);
            psDirector.executeUpdate();

            AtomicInteger movieIndex = new AtomicInteger(0);

            psMovie.setString(movieIndex.incrementAndGet(), movie.getName());
            psMovie.setLong(movieIndex.incrementAndGet(), movie.getCoordinates().getX());
            psMovie.setLong(movieIndex.incrementAndGet(), movie.getCoordinates().getY());
            psMovie.setLong(movieIndex.incrementAndGet(), movie.getOscarsCount());
            psMovie.setLong(movieIndex.incrementAndGet(), movie.getGoldenPalmCount());
            psMovie.setFloat(movieIndex.incrementAndGet(), movie.getBudget());
            psMovie.setInt(movieIndex.incrementAndGet(), movie.getMpaaRating().ordinal() + 1);
            psMovie.setObject(movieIndex.incrementAndGet(), director.getID());
            psMovie.setObject(movieIndex.get(), movie.getId());
            return psMovie.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.grey("Возникла проблема при обращении к баз данных"));
        }
        return Integer.MAX_VALUE;
    }

    public static boolean insertMovie(Movie movie) throws SQLException {
        Connection connection = getDBConnection();
        insertDirector(movie.getDirector(), connection);

        String r = """
                INSERT INTO movies
                (uuid_id, name, coordinatex, coordinatey, creation_date, oscars_count, golden_palm_count, budget, id_mpaarating, uuid_director, user_id) values
                (?, ?, ?, ?, ?, ?, ?, ?,?,?,?) ON CONFLICT DO NOTHING;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(r)) {
            AtomicInteger index = new AtomicInteger(0);

            preparedStatement.setObject(index.incrementAndGet(), movie.getId());
            preparedStatement.setString(index.incrementAndGet(), movie.getName());
            preparedStatement.setInt(index.incrementAndGet(), (int) movie.getCoordinates().getX());
            preparedStatement.setInt(index.incrementAndGet(), (int) movie.getCoordinates().getY());
            preparedStatement.setDate(index.incrementAndGet(), new java.sql.Date(movie.getCreationDate().getTime()));
            preparedStatement.setFloat(index.incrementAndGet(), movie.getOscarsCount());
            preparedStatement.setFloat(index.incrementAndGet(), movie.getGoldenPalmCount());
            preparedStatement.setFloat(index.incrementAndGet(), movie.getBudget());
            if (movie.getMpaaRating() == null) preparedStatement.setNull(index.incrementAndGet(), Types.INTEGER);
            else preparedStatement.setInt(index.incrementAndGet(), movie.getMpaaRating().ordinal() + 1);
            preparedStatement.setObject(index.incrementAndGet(), movie.getDirector().getID());
            preparedStatement.setString(index.get(), movie.getUserID());
            preparedStatement.execute();
            return true;
        } catch (PSQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("Ошибка при обращении к базе данных"));
        }
        return false;
    }

    private static boolean insertDirector(Person person, Connection connection) {
        String r = """
                INSERT INTO directors
                (uuid_director, name, birthday, height, passport_id, eye_color) values
                (?, ?, ?, ?, ?, ?) ON CONFLICT DO NOTHING;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(r)) {
            AtomicInteger index = new AtomicInteger(0);
            preparedStatement.setObject(index.incrementAndGet(), person.getID());
            preparedStatement.setString(index.incrementAndGet(), person.getName());
            if (person.getBirthday() == null) preparedStatement.setNull(index.incrementAndGet(), Types.DATE);
            else preparedStatement.setDate(index.incrementAndGet(), new java.sql.Date(person.getBirthday().getTime()));
            if (person.getHeight() == null) preparedStatement.setNull(index.incrementAndGet(), Types.FLOAT);
            else preparedStatement.setDouble(index.incrementAndGet(), person.getHeight());
            preparedStatement.setString(index.incrementAndGet(), person.getPassportID());
            preparedStatement.setInt(index.get(), person.getEyeColor().ordinal() + 1);
            return preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("Ошибка при обращении к базе данных"));
        }
        return false;
    }

    public static void initDB() {
        execute(createDirectorsTable);
        SQLUserManager.init();
        execute(createMovieTable);
    }
}
