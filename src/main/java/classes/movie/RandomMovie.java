package classes.movie;

import exceptions.*;

import java.util.Random;

public class RandomMovie {
    public static Movie generate() {
        Random r = new Random();
        try {
            Person director = new Person(
                    "Director" + r.nextInt(100),
                    null,
                    null,
                    Long.toString(r.nextLong(1000) + 100000000),
                    Color.BROWN);
            return new Movie(
                    "Name" + r.nextInt(100),
                    new Coordinates(r.nextInt(278), r.nextInt(1000) - 230),
                    r.nextLong(100000),
                    r.nextLong(100000),
                    r.nextFloat(10000),
                    null,
                    director);
        } catch (BlankValueException | NullValueException | NotGreatThanException | BadValueLengthException |
                 GreatThanException | NotUniqueException e) {
            System.out.println(e.toString());
        }
        return null;
    }
}
