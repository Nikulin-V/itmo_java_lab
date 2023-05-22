package classes.sql_managers;

public class SQLMovieManager extends SQLManager {
    private final static String createMovieTableQuery = """
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
                uuid_director uuid NOT NULL REFERENCES directors(uuid_director) ON DELETE SET NULL,
                uuid_user uuid NOT NULL REFERENCES users(uuid_user) ON DELETE CASCADE
            );""";

    private static final String createDirectorsTableQuery = """
            CREATE TABLE IF NOT EXISTS directors (
                uuid_director uuid PRIMARY KEY,
                name VARCHAR(255) NOT NULL CHECK (name <> ''),
                birthday DATE DEFAULT NULL,
                height DOUBLE PRECISION DEFAULT NULL CHECK (height > 0),
                passport_id VARCHAR(255) NOT NULL UNIQUE CHECK (length(passport_id) >= 7 AND passport_id <> ''),
                eye_color INTEGER NOT NULL
            );""";

    public SQLMovieManager() {}

    public void init() {
        execute(createMovieTableQuery);
    }
}
