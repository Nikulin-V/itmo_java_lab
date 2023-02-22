package movie;

import exceptions.NotGreatThanException;
import exceptions.NullValueException;

import static movie.FieldProperty.GREAT_THAN_X;
import static movie.FieldProperty.MAX_VALUE;

/**
 * Model of Coordinates. Sub-model of the <code>Route</code>. Contains getters/setters of each class fields.
 * Some fields have restrictions. It's signed under every method of field.
 */
public class Coordinates {
    private long x; //Максимальное значение поля: 279
    private int y; //Значение поля должно быть больше -230

    public Coordinates(long x, int y) throws NotGreatThanException, NullValueException {
        this.x = new FieldHandler(x, MAX_VALUE).handleLong();
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

    /**
     * Restrictions: The value of this field should be greater than -107.
     *
     * @param x Value of field x
     */
    public void setX(long x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
