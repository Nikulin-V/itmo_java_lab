package classes.movie;

import exceptions.GreatThanException;
import exceptions.NotGreatThanException;
import exceptions.NullValueException;

import static classes.movie.FieldProperty.GREAT_THAN_X;
import static classes.movie.FieldProperty.MAX_VALUE;

/**
 * Model of Coordinates. Sub-model of the <code>Route</code>. Contains getters/setters of each class fields.
 * Some fields have restrictions. It's signed under every method of field.
 */
public class Coordinates implements Comparable<Coordinates> {
    private long x; //Максимальное значение поля: 279
    private int y; //Значение поля должно быть больше -230

    private Coordinates(){}

    public Coordinates(long x, int y)  throws NotGreatThanException, NullValueException, GreatThanException  {
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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    @Override
    public int compareTo(Coordinates o) {
        if ((getX() == o.getX()) && (getY()==o.getY())){
            return 0;
        }else if (getX() != o.getX()){
            return (int) (getX() - o.getX());
        }else{
            return getY() - o.getY();
        }
    }
}
