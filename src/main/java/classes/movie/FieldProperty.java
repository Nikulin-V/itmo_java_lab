package classes.movie;

public enum FieldProperty {
    NOT_NULL,
    GREAT_THAN_ZERO(0),
    GREAT_THAN_X(-230),
    NOT_BLANK,
    UNIQUE,
    LENGTH(7),
    MAX_VALUE(279);

    private final Number[] args;

    FieldProperty(Number... args) {
        this.args = args;
    }

    public Number[] getArgs() {
        return args;
    }
}
