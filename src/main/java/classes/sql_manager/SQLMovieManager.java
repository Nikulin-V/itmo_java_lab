package classes.sql_manager;

import classes.console.TextColor;
import classes.movie.*;
import exceptions.*;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private static final String selectAllMovies = "SELECT * FROM movies";
    private static final String selectDirector = "SELECT * FROM directors WHERE uuid_director=?";

    public static void main(String[] args) throws SQLException {

        Movies movies = new Movies();
        ArrayList<Movie> m = new ArrayList<>();
        UUID adminID = new UUID(0L,0L);
        for (int i = 0; i < 18; i++) {
            //TODO SWAP to user's who executed ID
            m.add(RandomMovie.generate(adminID));
            System.out.println("inserted"+i);
        }
        movies.setMovies(m);
        new SQLMovieManager().saveCollectionToSQl(movies);
        try {
            movies = new SQLMovieManager().readCollectionFromSQl();
            System.out.println(movies.getMovies().size());
            System.out.println(new SQLMovieManager().readCollectionFromSQl());
        } catch (NotGreatThanException | GreatThanException | NullValueException | BlankValueException |
                 BadValueLengthException | NotUniqueException e) {
            throw new RuntimeException(e);
        }

        new SQLMovieManager().deleteMovie(UUID.randomUUID(), adminID);
        for (int i = 0; i < 10; i++) {
            new SQLUserManager().insertUser("vasya"+i, "sdfsdf"+787+i*18, "fsdfsfsdfsf"+78+i*8);
        }

        System.out.println("file end");
    }

    public void insertMovie(Movie movie) throws SQLException {
        UUID directorID = UUID.randomUUID();
        insertDirector(movie.getDirector(), directorID);

        String r = "INSERT INTO movies"
                + "(uuid_id, name, coordinatex, coordinatey, creation_date, oscars_count, golden_palm_count, budget, id_mpaarating, uuid_director, uuid_user)" + "values"
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
            preparedStatement.setObject(index++, directorID);
            preparedStatement.setObject(index++, movie.getUserId());
            preparedStatement.execute();
        } catch (NullPointerException | PSQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("!Ошибка при обращении к базе данных"));
        }
    }

    private void insertDirector(Person person, UUID id) {
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
            preparedStatement.execute();

        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
            System.out.println(TextColor.cyan("!Ошибка при обращении к базе данных"));
        }
    }

    private void deleteMovie(UUID id, UUID userID) {
        String r = "DELETE FROM movies WHERE uuid_id = ? AND uuid_user = ?";
        try (PreparedStatement statement = createDBConnection().prepareStatement(r)) {
            int index = 1;
            statement.setObject(index++, id);
            statement.setObject(index++, userID);
            int result = statement.executeUpdate();
            if (result == 1) System.out.println("Deleted");
            else System.out.println("Фильма с таким id не существует или у вас нет прав доступа к элементам коллекции других пользователей");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getDirectorSequenceNextValue() {
        String sql = "SELECT NEXTVAL('director_seq')";
        int nextID = -9999;
        try (PreparedStatement ps = createDBConnection().prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                nextID = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(TextColor.purple("Проблема с доступом к базе данных"));
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


    public Movies readCollectionFromSQl() throws NotGreatThanException, GreatThanException, NullValueException, BlankValueException, BadValueLengthException, NotUniqueException, SQLException {
        Movies movies = new Movies();
        List<Movie> movieList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            PreparedStatement prepareStatement = connection.prepareStatement(selectDirector);
            ResultSet rs1 = statement.executeQuery(selectAllMovies);
            while (rs1.next()) {
                UUID id_director = (UUID) rs1.getObject("uuid_director");
                prepareStatement.setObject(1,id_director);
                ResultSet rs2 = prepareStatement.executeQuery();
                Person director = null;
                if (rs2.next()) {
                     director = new Person(
                            (UUID) rs2.getObject("uuid_director"),
                            rs2.getString("name"),
                            rs2.getDate("birthday"),
                            rs2.getDouble("height"),
                            rs2.getString("passport_id"),
                            Color.getById(rs2.getInt("eye_color"))
                    );
                }
                Coordinates coordinates = new Coordinates(rs1.getInt("coordinatex"),
                        rs1.getInt("coordinatey"));
                //TODO change caller to changing value
                UUID callerUUID = UUID.randomUUID();
                Movie toAdd = new Movie(
                        (UUID) rs1.getObject("uuid_id"),
                        rs1.getString("name"),
                        coordinates,
                        rs1.getDate("creation_date"),
                        rs1.getLong("oscars_count"),
                        rs1.getLong("golden_palm_count"),
                        rs1.getFloat("budget"),
                        MpaaRating.getById(rs1.getInt("id_mpaarating")),
                        director,
                        callerUUID
                        );
                movieList.add(toAdd);
                rs2.close();
            }
            rs1.close();
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
