package classes.movie;

import exceptions.*;

import java.util.Date;
import java.util.Random;

public class RandomMovie {
    public static Movie generate() {
        Random r = new Random();
        try {
            Person director = new Person(
                    "Director" + r.nextInt(1000),
                    generateDate(),
                    r.nextDouble(1000),
                    Long.toString(r.nextLong(1000000) + 100000000),
                    Color.getRandom());
            return new Movie(
                    "Name" + r.nextInt(100),
                    new Coordinates(r.nextInt(278), r.nextInt(1000) - 230),
                    r.nextLong(100000),
                    r.nextLong(100000),
                    r.nextFloat(10000),
                    MpaaRating.getRandom(),
                    director,
                    //TODO fix that value to whom ran that command
                    8800);
        } catch (BlankValueException | NullValueException | NotGreatThanException | BadValueLengthException |
                 GreatThanException | NotUniqueException e) {
            System.out.println(e);
        }
        return null;
    }

    public static Date generateDate() {

        // Get an Epoch value roughly between 1940 and 2010
        // -946771200000L = January 1, 1940
        // Add up to 70 years to it (using modulus on the next long)
        // generate date from milliseconds
        return new Date(-946771200000L + (Math.abs(new Random().nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000)) );
    }
}
