package classes.movie;

import java.io.Serializable;

public enum MpaaRating implements Serializable {

    G("General Audiences"),
    PG("Parental Guideness Suggested"),
    PG_13("Parents Strongly Cautioned"),
    R("Restricted"),
    NC_17("No One 17 And Under Admitted");

    private final String description;
    private static final long serialVersionUID = 20161019L;
    MpaaRating(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
