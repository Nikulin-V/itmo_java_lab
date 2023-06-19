package classes.movie;

import exceptions.GreatThanException;
import exceptions.NotGreatThanException;
import exceptions.NullValueException;

import java.io.Serializable;

import static classes.movie.FieldProperty.GREAT_THAN_X;
import static classes.movie.FieldProperty.MAX_VALUE;

/**
 * Model of Coordinates. Sub-model of the <code>Route</code>. Contains getters/setters of each class fields.
 * Some fields have restrictions. It's signed under every method of field.
 */
public class Coordinates implements Comparable<Coordinates>, Serializable {
    private static final long serialVersionUID = 20161019L;
    private int x; //Максимальное значение поля: 279
    private int y; //Значение поля должно быть больше -230

    private Coordinates() {
    }

    public Coordinates(int x, int y) throws NotGreatThanException, NullValueException, GreatThanException {
        this.x = new FieldHandler(x, MAX_VALUE).handleInt();
        this.y = new FieldHandler(y, GREAT_THAN_X).handleInt();
    }

    /**
     * Restrictions: Max value of this field is 279, cannot be null
     *
     * @return Value of field x
     */
    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        return (
                obj instanceof Coordinates &&
                this.getX() == ((Coordinates) obj).getX() &&
                this.getY() == ((Coordinates) obj).getY()
        );
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public int compareTo(Coordinates o) {
        if ((getX() == o.getX()) && (getY()==o.getY()))
            return 0;
        else if (getX() != o.getX())
                return (int) (o.getX() - getX());
        return (int) (o.getY() - getY());
    }
}
