package classes.movie;

import java.io.Serializable;

public enum Color implements Serializable {
    RED,
    BLACK,
    BLUE,
    BROWN;

    private static final long serialVersionUID = 20161020L;

    @Override
    public String toString() {
        return this.name();
    }
}
