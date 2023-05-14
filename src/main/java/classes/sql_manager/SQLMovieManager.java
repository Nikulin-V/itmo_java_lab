package classes.sql_manager;

import classes.console.TextColor;
import classes.movie.*;
import exceptions.*;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static classes.sql_manager.SQLManager.createDBConnection;


/**
 * Model of SQL manager. Sub-model of the <code>Route</code>. Singleton to read-write of some class.
 * Some fields have restrictions. It's signed under every method of field.
 */
public class SQLMovieManager {
    private final Connection connection;

    public SQLMovieManager() {
        this.connection = createDBConnection();
    }

    public Connection getConnection() {
        return this.connection;
    }


    private static final String selectAllMovies = "SELECT * FROM movies";
    private static final String selectDirector = "SELECT * FROM directors WHERE id_director= ";

    public static void main(String[] args) throws SQLException {


        for (int i = 0; i < 10; i++) {
            new SQLMovieManager().insertMovie(RandomMovie.generate());
        }

        System.out.println("finished");


    }


//    private static String getCurrentTimeStamp() {
//        Date today = new Date();
//        return today.toString();
//    }

    private void insertMovie(Movie movie) throws SQLException {
        int num = new Random().nextInt(1000000);
        insertDirector(movie.getDirector(), num);

        String r = "INSERT INTO movies"
                + "(id, name, coordinatex, coordinatey, creation_date, oscars_count, golden_palm_count, budget, id_mpaarating, id_director, id_user)" + "values"
                + "(?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(r)) {
            preparedStatement.setString(1, movie.getId().toString());
            preparedStatement.setString(2, movie.getName());
            preparedStatement.setInt(3, (int) movie.getCoordinates().getX());
            preparedStatement.setInt(4, (int) movie.getCoordinates().getY());
            preparedStatement.setDate(5, new java.sql.Date(movie.getCreationDate().getTime()));
            preparedStatement.setFloat(6, movie.getOscarsCount());
            preparedStatement.setFloat(7, movie.getGoldenPalmCount());
            preparedStatement.setFloat(8, movie.getBudget());
            if (movie.getMpaaRating() == null) {
                preparedStatement.setNull(9, Types.INTEGER);
            } else preparedStatement.setInt(9, movie.getMpaaRating().ordinal() + 1);
            preparedStatement.setInt(10, num);
            preparedStatement.setInt(11, movie.getUserId());
            preparedStatement.execute();
        } catch (NullPointerException | PSQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("!Ошибка при обращении к базе данных"));
        }
    }

    private void insertDirector(Person person, int num) {
        String r = "INSERT INTO directors"
                + "(id_director, name, birthday, height, passport_id, eye_color)" + "values"
                + "(?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(r)) {
            preparedStatement.setInt(1, num);
            preparedStatement.setString(2, person.getName());
            if (person.getBirthday() == null) preparedStatement.setNull(3, Types.DATE);
            else preparedStatement.setDate(3, new java.sql.Date(person.getBirthday().getTime()));
            if (person.getHeight() == null) preparedStatement.setNull(4, Types.FLOAT);
            else preparedStatement.setDouble(4, person.getHeight());
            preparedStatement.setString(5, person.getPassportID());
            preparedStatement.setInt(6, person.getEyeColor().ordinal() + 1);
            preparedStatement.execute();
        } catch (NullPointerException | PSQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("!Ошибка при обращении к базе данных"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void deleteMovie(Movie movie) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String r = "DELETE movies WHERE id = " + movie.getId();
        try {
            dbConnection = createDBConnection();
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

    public static int getDirectorSequenceNextValue() {
        String sql = "SELECT NEXTVAL('director_seq')";
        PreparedStatement ps;
        int nextID = -9999;
        try {
            ps = createDBConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                nextID = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nextID;

    }

    public static int getMovieSequenceNextValue() {
        String sql = "SELECT NEXTVAL('movie_seq')";
        PreparedStatement ps;
        int nextID = -9999;
        try {
            ps = createDBConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                nextID = rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nextID;

    }


    public Movies readCollectionFromSQl() throws NotGreatThanException, GreatThanException, NullValueException, BlankValueException, BadValueLengthException, NotUniqueException {
        Movies movies = new Movies();
        List<Movie> movieList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs1 = statement.executeQuery(selectAllMovies);
            while (rs1.next()) {
                int id_director = rs1.getInt("id_director");
                ResultSet rs2 = statement.executeQuery(selectDirector + id_director);

                Person director = new Person(
                        rs2.getString("name"),
                        rs2.getDate("birthday"),
                        rs2.getDouble("height"),
                        rs2.getString("passport_id"),
                        Color.getById(rs2.getInt("eye_color"))
                );
                rs1.getString("id");
                Coordinates coordinates = new Coordinates(rs1.getInt("coordinatex"),
                        rs1.getInt("coordinatey"));
                int callerID = 0;
                Movie toAdd = new Movie(
                        rs1.getString("name"),
                        coordinates,
//                        rs1.getDate("creation_date"),
                        rs1.getLong("oscars_count"),
                        rs1.getLong("golden_palm_count"),
                        rs1.getFloat("budget"),
                        MpaaRating.getById(rs1.getInt("id_mpaarating")),
                        director,
                        callerID
                        );
                movieList.add(toAdd);
            }

            movies.setMovies(movieList);
            return movies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void saveCollectionToSQl(Movies movies)  {
        for (Movie elem : movies.getMovies()) {
            try {
                insertMovie(elem);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println(TextColor.red("Не удалось сохранить эти элементы в коллекцию"));
            }
        }

    }


}
