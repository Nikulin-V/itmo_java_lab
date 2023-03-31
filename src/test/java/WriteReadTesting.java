import classes.movie.*;
import classes.xml_manager.XMLMovieManager;
import exceptions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class WriteReadTesting {
    public static void main(String[] args) throws BlankValueException, NotGreatThanException, BadValueLengthException, NullValueException, GreatThanException, NotUniqueException {
        List<Movie> mmovies = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Date directorBirthday = new Date();
            Random height = new Random();
            Person director = new Person(
                    "Director" + i,
                    null,
                    null,
                    "1234567",
                    Color.BROWN);
            Movie m = new Movie(
                    "Name" + i,
                    new Coordinates(i, i + 1),
                    null,
                    null,
                    null,
                    null,
                    director);
            Random r = new Random();
            m.setBudget((float) r.nextInt(10000)); //[0;..]
            mmovies.add(m);
        }
        Movies mmm = new Movies();
        mmm.setMovies(mmovies);
        System.out.println(XMLMovieManager.getInstance().readCollectionFromXML().toString());
        XMLMovieManager.getInstance().saveCollectionToXML(mmm);
    }
}
