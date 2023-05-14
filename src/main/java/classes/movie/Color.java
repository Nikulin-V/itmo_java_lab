package classes.movie;

import java.io.Serializable;
import java.util.Random;

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

    public static Color getRandom() {
        return Color.values()[new Random().nextInt(4)];
    }
    public static Color getById(int id) {
        return Color.values()[id-1];
    }
}
