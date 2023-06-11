package classes.movie;


import classes.console.TextColor;
import exceptions.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Model of Movie. Sub-model of the <code>Route</code>. Contains getters/setters of each class fields.
 * Some fields have restrictions. It's signed under every method of field.
 */
@XmlRootElement(name = "classes/movie")
@XmlAccessorType(XmlAccessType.FIELD)
public class Movie implements Serializable {
    private static final long serialVersionUID = 20161013L;
    private UUID id; //(UUID) Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long oscarsCount; //Значение поля должно быть больше 0
    private Long goldenPalmCount; //Значение поля должно быть больше 0
    private Float budget; //Значение поля должно быть больше 0, Поле может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person director; //Поле не может быть null
    private final String userID; // Поле не может быть null

    public Movie(String name,
                 Coordinates coordinates,
                 Long oscarsCount,
                 Long goldenPalmCount,
                 Float budget,
                 MpaaRating mpaaRating,
                 Person director,
                 String userID) throws BlankValueException, NullValueException, NotGreatThanException, BadValueLengthException, GreatThanException, NotUniqueException {
        id = UUID.randomUUID();
        this.name = new FieldHandler(name, FieldProperty.NOT_NULL, FieldProperty.NOT_BLANK).handleString();
        this.coordinates = (Coordinates) new FieldHandler(coordinates, FieldProperty.NOT_NULL).handleObject();
        creationDate = new Date();
        this.oscarsCount = new FieldHandler(oscarsCount, FieldProperty.GREAT_THAN_ZERO).handleLong();
        this.goldenPalmCount = new FieldHandler(goldenPalmCount, FieldProperty.GREAT_THAN_ZERO).handleLong();
        this.budget = new FieldHandler(budget, FieldProperty.GREAT_THAN_ZERO).handleFloat();
        this.mpaaRating = mpaaRating;
        this.director = (Person) new FieldHandler(director, FieldProperty.NOT_NULL).handleObject();
        this.userID = userID;
    }

    public Movie(UUID id,
                 String name,
                 Coordinates coordinates,
                 Date creationDate,
                 Long oscarsCount,
                 Long goldenPalmCount,
                 Float budget,
                 MpaaRating mpaaRating,
                 Person director,
                 String userID) throws BlankValueException, NullValueException, NotGreatThanException, BadValueLengthException, GreatThanException, NotUniqueException {
        this.id = id;
        this.name = new FieldHandler(name, FieldProperty.NOT_NULL, FieldProperty.NOT_BLANK).handleString();
        this.coordinates = (Coordinates) new FieldHandler(coordinates, FieldProperty.NOT_NULL).handleObject();
        this.creationDate = creationDate;
        this.oscarsCount = new FieldHandler(oscarsCount, FieldProperty.GREAT_THAN_ZERO).handleLong();
        this.goldenPalmCount = new FieldHandler(goldenPalmCount, FieldProperty.GREAT_THAN_ZERO).handleLong();
        this.budget = new FieldHandler(budget, FieldProperty.GREAT_THAN_ZERO).handleFloat();
        this.mpaaRating = mpaaRating;
        this.director = (Person) new FieldHandler(director, FieldProperty.NOT_NULL).handleObject();
        this.userID = userID;
    }

    /**
     * Restrictions:  Value must be more than zero
     *
     * @return Value of field id
     */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Restrictions:  Field cannot be null or empty string
     *
     * @return Value of field name
     */
    public String getName() {
        return name;
    }

    /**
     * Restrictions:  Field cannot be null
     *
     * @return Value of field coordinates
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Restrictions: Field should be unique and generating automatically
     *
     * @param coordinates Value of field coordinates
     */

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * Restrictions:  Field cannot be null, value should generate automatically
     *
     * @return Value of field creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Restrictions: Field should be unique and generating automatically
     *
     * @param creationDate Value of field creationDate
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Restrictions:  Field should be greater than zero
     *
     * @return Value of field creationDate
     */
    public Long getOscarsCount() {
        return oscarsCount;
    }

    /**
     * Restrictions: Field should be greater than zero
     *
     * @param oscarsCount Value of field oscarsCount
     */
    public void setOscarsCount(long oscarsCount) {
        this.oscarsCount = oscarsCount;
    }

    /**
     * Restrictions: Field should be greater than zero
     *
     * @return Value of field goldenPalmCount
     */
    public Long getGoldenPalmCount() {
        return goldenPalmCount;
    }

    /**
     * Restrictions: Field should be greater than zero
     *
     * @param goldenPalmCount Value of field goldenPalmCount
     */
    public void setGoldenPalmCount(long goldenPalmCount) {
        this.goldenPalmCount = goldenPalmCount;
    }

    /**
     * Restrictions: Field should be greater than zero and cannot be null
     *
     * @return Value of field budget
     */
    public Float getBudget() {
        return budget;
    }

    /**
     * Restrictions: Field should be greater than zero
     *
     * @param budget Value of field goldenPalmCount
     */
    public void setBudget(Float budget) {
        this.budget = budget;
    }

    /**
     * Restrictions:  Field can be null
     *
     * @return Value of field mpaaRating
     */
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    /**
     * Restrictions: Field should be greater than zero
     *
     * @param mpaaRating Value of field mpaaRating
     */
    public void setMpaaRating(MpaaRating mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     * Restrictions:  Field cannot be null
     *
     * @return Value of field director
     */
    public Person getDirector() {
        return director;
    }

    /**
     * Restrictions: Field cannot be null
     *
     * @param director Value of field director
     */
    public void setDirector(Person director) {
        this.director = director;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        String[] fieldNames = {"ID", "Creator", "Name", "Coordinates", "CreationDate", "OscarsCount", "GoldenPalmCount", "Budget", "MpaaRating", "Director"};
        Object[] fieldValues = {id, userID, name, coordinates, creationDate, oscarsCount, goldenPalmCount, budget, mpaaRating, director};
        StringBuilder movieString = new StringBuilder(TextColor.cyan("Movie {\n"));
        for (int fieldId = 0; fieldId < fieldNames.length; fieldId++) {
            if (fieldValues[fieldId] != null)
                movieString.append("\t\t").append(TextColor.grey(fieldNames[fieldId] + "=")).append(TextColor.cyan(fieldValues[fieldId].toString())).append("\n");
        }
        movieString.append(TextColor.cyan("\t}"));
        return movieString.toString();
    }


    public String[] toSQLRepresentation() {
        Object[] fieldValues = {id, userID, name, coordinates, creationDate, oscarsCount, goldenPalmCount, budget, mpaaRating, director};
        String[] arr = new String[fieldValues.length];

        for (int i = 0; i < fieldValues.length; i++) {
            arr[i] = fieldValues[i].toString();
        }

        return arr;
    }
    public static String[] getSQLColumn() {
        return new String[]{"ID", "Creator", "Name", "Coordinates", "CreationDate", "OscarsCount", "GoldenPalmCount", "Budget", "MpaaRating", "Director"};
    }
//    public static ArrayList<String> getSQLColumn() {
//        return new ArrayList<>() {
//            {
//                add("ID");
//                add("Creator");
//                add("Name");
//                add("Coordinates");
//                add("CreationDate");
//                add("OscarsCount");
//                add("GoldenPalmCount");
//                add("Budget");
//                add("MpaaRating");
//                add("Director");
//            }
//        };
//    }
}

