package classes.movie;

public enum Color {
    RED,
    BLACK,
    BLUE,
    BROWN;

    @Override
    public String toString() {
        return this.name();
    }
}
