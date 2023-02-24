package classes.movie;

import classes.console.TextColor;
import exceptions.*;

import java.util.Date;

import static classes.movie.FieldProperty.*;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Date birthday; //Поле может быть null
    private Double height; //Поле может быть null, Значение поля должно быть больше 0
    private String passportID; //Значение этого поля должно быть уникальным, Длина строки должна быть не меньше 7, Строка не может быть пустой, Поле не может быть null
    private Color eyeColor; //Поле не может быть null

    public Person(String name, Date birthday, Double height, String passportID, Color eyeColor) throws BlankValueException, NullValueException, NotGreatThanException, BadValueLengthException, GreatThanException, NotUniqueException {
        this.name = new FieldHandler(name, NOT_NULL, NOT_BLANK).handleString();
        this.birthday = birthday;
        this.height = new FieldHandler(height, GREAT_THAN_ZERO).handleDouble();
        this.passportID = new FieldHandler(passportID, UNIQUE, LENGTH, NOT_BLANK, NOT_NULL).handleString();
        this.eyeColor = (Color) new FieldHandler(eyeColor, NOT_NULL).handleObject();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        this.eyeColor = eyeColor;
    }

    @Override
    public String toString() {
        String[] fieldNames = {"Name", "Birthday", "Height", "PassportID", "EyeColor"};
        Object[] fieldValues = {name, birthday, height, passportID, eyeColor};
        String personString = TextColor.green("Person {\n");
        for (int fieldId = 0; fieldId < fieldNames.length; fieldId++) {
            if (fieldValues[fieldId] != null)
                personString += "\t\t\t" + TextColor.grey(fieldNames[fieldId] + "=") + TextColor.green(fieldValues[fieldId].toString()) + "\n";
        }
        personString += TextColor.green("\t\t}");
        return personString;
    }
}
