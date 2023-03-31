package classes.movie;

public enum MpaaRating {
    G("General Audiences"),
    PG("Parental Guideness Suggested"),
    PG_13("Parents Strongly Cautioned"),
    R("Restricted"),
    NC_17("No One 17 And Under Admitted");

    private final String description;

    MpaaRating(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
