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


    private static String createMovieTable = "CREATE TABLE movies (" +
            "    id integer PRIMARY KEY," +
            "    name VARCHAR(255) NOT NULL CHECK (name <> '')," +
            "    coordinates jsonb NOT NULL,\n" +
            "    creationdate TIMESTAMP WITH TIME ZONE DEFAULT NOW()," +
            "    oscarscount BIGINT NOT NULL CHECK (oscarscount > 0),\n" +
            "    goldenpalmcount BIGINT NOT NULL CHECK (goldenpalmcount > 0),\n" +
            "    budget FLOAT NOT NULL CHECK (budget > 0) DEFAULT NULL,\n" +
            "    mpaarating VARCHAR(10) DEFAULT NULL,\n" +
            "    director FLOAT NOT NULL,\n" +
            "    creator VARCHAR(255) NOT NULL CHECK (creator <> '')\n" +
            ");";

    private static String createDirectorsTable = "CREATE TABLE directors (\n" +
            "    id SERIAL PRIMARY KEY,\n" +
            "    name VARCHAR(255) NOT NULL CHECK (name <> ''),\n" +
            "    birthday DATE DEFAULT NULL,\n" +
            "    height DOUBLE PRECISION DEFAULT NULL CHECK (height > 0),\n" +
            "    passportid VARCHAR(255) NOT NULL UNIQUE CHECK (length(passportid) >= 7 AND passportid <> ''),\n" +
            "    eyecolor VARCHAR(255) NOT NULL\n" +
            ");";

    private static String createCreatorTable = "CREATE TABLE directors (\n" +
            "    id SERIAL PRIMARY KEY,\n" +
            "    name VARCHAR(255) NOT NULL CHECK (name <> '')\n" +
            ");";
    private static String createEyeTypeTable = "CREATE TABLE directors (\n" +
            "    id SERIAL PRIMARY KEY,\n" +
            "    color_name VARCHAR(255) NOT NULL CHECK (name <> '')\n" +
            ");";
    private static String createMPAARatingTypeTable = "CREATE TABLE MPAARatingType(\n" +
            "id_mpaa SERIAL  PRIMARY KEY,\n" +
            "type_name VARCHAR(255) NOT NULL CHECK (name <> ''),\n" +
            ");";


    String select = "select value from test where id=1\n" +
            "insert into movies ()\n" +
            "values ();";

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
            String url = "jdbc:postgresql://127.0.0.1:5432/studs";
            Properties props = new Properties();
            props.setProperty("user", "s368670");
            props.setProperty("password", "lf6E0op8RuygLo32");
            dbConnection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            System.out.println(TextColor.green(e.getMessage()));
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
