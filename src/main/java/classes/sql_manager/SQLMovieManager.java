package classes.sql_manager;

import classes.console.TextColor;
import classes.movie.Movie;
import classes.movie.Movies;
import classes.movie.RandomMovie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;


/**
 * Model of SQL manager. Sub-model of the <code>Route</code>. Singleton to read-write of some class.
 * Some fields have restrictions. It's signed under every method of field.
 */
public class SQLMovieManager {
    private static classes.xml_manager.XMLMovieManager INSTANCE;
    private final Class BASE_CLASS = Movies.class;


    private final static String createMovieTable = """
            CREATE TABLE movies(
                id integer PRIMARY KEY,
                name VARCHAR(255) NOT NULL CHECK (name <> ''),
                coordinates jsonb NOT NULL,
                creationdate TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
                oscarscount BIGINT NOT NULL CHECK (oscarscount > 0),
                goldenpalmcount BIGINT NOT NULL CHECK (goldenpalmcount > 0),
                budget FLOAT NOT NULL CHECK (budget > 0) DEFAULT NULL,
                mpaarating VARCHAR(10) DEFAULT NULL,
                director FLOAT NOT NULL,
                creator VARCHAR(255) NOT NULL CHECK (creator <> '')
            );""";

    private static final String createDirectorsTable = """
            CREATE TABLE directors (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL CHECK (name <> ''),
                birthday DATE DEFAULT NULL,
                height DOUBLE PRECISION DEFAULT NULL CHECK (height > 0),
                passportid VARCHAR(255) NOT NULL UNIQUE CHECK (length(passportid) >= 7 AND passportid <> ''),
                eyecolor VARCHAR(255) NOT NULL
            );""";

    private static final String createCreatorTable = """
            CREATE TABLE directors(
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL CHECK (name <> '')
            );""";
    private static final String createEyeTypeTable = """
            CREATE TABLE directors(
                id SERIAL PRIMARY KEY,
                color_name VARCHAR(255) NOT NULL CHECK (name <> '')
            );""";
    private static final String createMPAARatingTypeTable = """
            CREATE TABLE MPAARatingType(
                id_mpaa SERIAL  PRIMARY KEY,
                type_name VARCHAR(255) NOT NULL CHECK (name <> ''),
            );""";


    String select = """
            SELECT value FROM test WHERE id=1
            INSERT INTO movies()
            VALUES();""";

    public static void main(String[] args) {
        try {
            init();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void init() throws SQLException {
        execute(createMovieTable);
        execute(createDirectorsTable);
        execute(createMPAARatingTypeTable);
        execute(createEyeTypeTable);
        insertMovie(Objects.requireNonNull(RandomMovie.generate()));
    }
    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            String url = "jdbc:postgresql:/%s:%d/studs".formatted(System.getenv("DB_HOST"), Integer.parseInt(System.getenv("DB_PORT")));
            Properties props = new Properties();
            props.setProperty("user", System.getenv("DB_USER"));
            props.setProperty("password", System.getenv("DB_PASS"));
            dbConnection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.println(TextColor.green(e.getMessage()));
        } catch (NumberFormatException e) {
            System.out.println(TextColor.red("Переменная окружения DB_PORT должна быть в формате целого числа 0-65535"));
        }
        return dbConnection;
    }


    private static void execute(String command) {
        try (Connection dbConnection = getDBConnection(); Statement statement = dbConnection != null ? dbConnection.createStatement() : null) {
            statement.execute(command);
            System.out.println(TextColor.cyan("Command executed"));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void selectFrom(String from) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        // if table exists
        String r = "SELECT * from " + from;
        try {
            dbConnection = getDBConnection();
            assert dbConnection != null;
            statement = dbConnection.createStatement();

            statement.execute(r);
            System.out.println("Table \"dbuser\" is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static String getCurrentTimeStamp() {
        Date today = new Date();
        return today.toString();
    }
//TODO insertMovie deleteMovie - один и тот же метод почти?
    private static void insertMovie(Movie movie) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String r = "INSERT INTO movies"
                + "(id, name, coordinates, creationdate, oscarscount, goldenpalmcount, budget, mpaarating, id_director)" + "VALUES"
                + "()".formatted(movie.getId(),
                movie.getName(),
                movie.getCoordinates(),
                movie.getCreationDate(),
                movie.getOscarsCount(),
                movie.getGoldenPalmCount(),
                movie.getBudget(),
                movie.getMpaaRating());
        try {
            dbConnection = getDBConnection();
            assert dbConnection != null;
            statement = dbConnection.createStatement();

            statement.execute(r);
            System.out.println("inserted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    private static void deleteMovie(Movie movie) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String r = "DELETE movies WHERE id = " + movie.getId();
        try {
            dbConnection = getDBConnection();
            assert dbConnection != null;
            statement = dbConnection.createStatement();
            statement.execute(r);
            System.out.println("deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }
}
