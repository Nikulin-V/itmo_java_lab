package classes.console;

import classes.UserCredentials;
import classes.movie.*;
import exceptions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static classes.movie.FieldProperty.*;

public class InputHandler {
    static final RepeatScanner scanner = new RepeatScanner();

    public static Movie readMovie(String userID) {
        Movie movie = null;
        while (movie == null) {
            try {
                movie = new Movie(
                        readMovieName(),
                        readCoordinates(),
                        readOscarsCount(),
                        readGoldenPalmCount(),
                        readBudget(),
                        readMpaaRating(),
                        readDirector(),
                        userID
                );
            } catch (BlankValueException | NullValueException | NotGreatThanException | BadValueLengthException |
                     GreatThanException | NotUniqueException e) {
                System.out.println(e.toString());
            }
        }
        return movie;
    }

    public static Movie updateMovie(Movie movie) {
        movie.setName(readMovieName());
        movie.setCoordinates(readCoordinates());
        movie.setOscarsCount(readOscarsCount());
        movie.setGoldenPalmCount(readGoldenPalmCount());
        movie.setBudget(readBudget());
        movie.setMpaaRating(readMpaaRating());
        movie.setDirector(readDirector());
        return movie;
    }

    private static Person readDirector() {
        System.out.println(TextColor.green("Director: "));
        Person director = null;
        while (director == null)
            try {
                director = new Person(
                        readDirectorName(),
                        readDirectorBirthday(),
                        readDirectorHeight(),
                        readDirectorPassportID(),
                        readDirectorEyeColor()
                );
            } catch (BlankValueException | NullValueException | NotGreatThanException | BadValueLengthException |
                     GreatThanException | NotUniqueException e) {
                System.out.println(e.toString());
            }
        return director;
    }

    private static String readMovieName() {
        String name = null;
        while (name == null) {
            try {
                name = new FieldHandler(
                        scanner.nextLine(TextColor.green("Name: ")),
                        NOT_NULL, NOT_BLANK
                ).handleString();
            } catch (NullValueException | BlankValueException | BadValueLengthException | NotUniqueException e) {
                System.out.println(e.toString());
            }
        }
        return name;
    }

    private static Coordinates readCoordinates() {
        System.out.println(TextColor.green("Coordinates: "));
        Coordinates coordinates = null;
        while (coordinates == null) {
            try {
                coordinates = new Coordinates(readCoordinatesX(), readCoordinatesY());
            } catch (NotGreatThanException | NullValueException | GreatThanException e) {
                System.out.println(e.toString());
            }
        }
        return coordinates;
    }

    private static Long readCoordinatesX() {
        Long coordinatesX = null;
        while (coordinatesX == null) {
            try {
                coordinatesX = new FieldHandler(
                        scanner.nextLong(TextColor.green("\tX: ")),
                        MAX_VALUE
                ).handleLong();
            } catch (NotGreatThanException | NullValueException | GreatThanException e) {
                System.out.println(e.toString());
            }
        }
        return coordinatesX;
    }

    private static Integer readCoordinatesY() {
        Integer coordinatesY = null;
        while (coordinatesY == null) {
            try {
                coordinatesY = new FieldHandler(
                        scanner.nextInt(TextColor.green("\tY: ")),
                        GREAT_THAN_X
                ).handleInt();
            } catch (NotGreatThanException | NullValueException | GreatThanException e) {
                System.out.println(e.toString());
            }
        }
        return coordinatesY;
    }

    private static Long readOscarsCount() {
        boolean wasInput = false;
        Long oscarsCount = null;
        while (!wasInput) {
            try {
                oscarsCount = new FieldHandler(scanner.nextLong(TextColor.green("Oscars count: ")), GREAT_THAN_ZERO).handleLong();
                wasInput = true;
            } catch (NullValueException | NotGreatThanException | GreatThanException e) {
                System.out.println(e.toString());
            }
        }
        return oscarsCount;
    }

    private static Long readGoldenPalmCount() {
        boolean wasInput = false;
        Long goldenPalmCount = null;
        while (!wasInput) {
            try {
                goldenPalmCount = new FieldHandler(
                        scanner.nextLong(TextColor.green("Golden palm count: ")),
                        GREAT_THAN_ZERO
                ).handleLong();
                wasInput = true;
            } catch (NullValueException | NotGreatThanException | GreatThanException e) {
                System.out.println(e.toString());
            }
        }
        return goldenPalmCount;
    }

    private static Float readBudget() {
        boolean wasInput = false;
        Float budget = null;
        while (!wasInput) {
            try {
                budget = new FieldHandler(
                        scanner.nextFloat(TextColor.green("Budget: ")),
                        GREAT_THAN_ZERO
                ).handleFloat();
                wasInput = true;
            } catch (NullValueException | NotGreatThanException | GreatThanException e) {
                System.out.println(e.toString());
            }
        }
        return budget;
    }

    private static MpaaRating readMpaaRating() {
        boolean wasInput = false;
        Integer mpaaRatingId;
        MpaaRating mpaaRating = null;
        while (!wasInput) {
            System.out.println(TextColor.yellow(
                    """
                            Options:
                            1 General Audiences
                            2 Parental Guideness Suggested
                            3 Parents Strongly Cautioned
                            4 Restricted
                            5 No One 17 And Under Admitted"""));
            mpaaRatingId = scanner.nextInt(TextColor.green("MPAA Rating: "));
            if (mpaaRatingId != null)
                try {
                    mpaaRating = MpaaRating.values()[mpaaRatingId - 1];
                } catch (IndexOutOfBoundsException e) {
                    new WarningException("Выберите опцию 1-5").printMessage();
                }
            if (mpaaRatingId == null || mpaaRating != null)
                wasInput = true;
        }
        return mpaaRating;
    }

    private static String readDirectorName() {
        String directorName = null;
        while (directorName == null) {
            try {
                directorName = new FieldHandler(
                        scanner.nextLine(TextColor.green("\tName: ")),
                        NOT_NULL, NOT_BLANK
                ).handleString();
            } catch (NullValueException | BlankValueException | BadValueLengthException |
                     NotUniqueException e) {
                System.out.println(e.toString());
            }
        }
        return directorName;
    }

    private static Date readDirectorBirthday() {
        boolean wasInput = false;
        Date directorBirthday = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        while (!wasInput) {
            try {
                String inputString = scanner.nextLine(TextColor.green("Birthday (yyyy-MM-dd): "));
                String[] inputDate = inputString.split("-");
                if (inputDate.length == 3) {
                    int month = Integer.parseInt(inputDate[1]);
                    int day = Integer.parseInt(inputDate[2]);
                    directorBirthday = formatter.parse(inputString);
                    if (1 <= month && month <= 12 && 1 <= day && day <= 31)
                        wasInput = true;
                    else System.out.println(TextColor.yellow("Неверный формат даты"));
                } else System.out.println(TextColor.yellow("Неверный формат даты"));
            } catch (ParseException e) {
                System.out.println(TextColor.yellow("Неверный формат даты"));
            }
        }
        return directorBirthday;
    }

    private static Double readDirectorHeight() {
        boolean wasInput = false;
        Double directorHeight = null;
        while (!wasInput) {
            try {
                directorHeight = new FieldHandler(scanner.nextDouble(TextColor.green("Height: ")), GREAT_THAN_ZERO).handleDouble();
                wasInput = true;
            } catch (NullValueException | NotGreatThanException | GreatThanException e) {
                System.out.println(e.toString());
            }
        }
        return directorHeight;
    }

    private static String readDirectorPassportID() {
        String directorPassportID = null;
        while (directorPassportID == null) {
            try {
                directorPassportID = new FieldHandler(
                        scanner.nextLine(TextColor.green("Passport ID: ")),
                        UNIQUE, LENGTH, NOT_BLANK, NOT_NULL
                ).handleString();
            } catch (NullValueException | BlankValueException | BadValueLengthException |
                     NotUniqueException e) {
                System.out.println(e.toString());
            }
        }
        return directorPassportID;
    }

    private static Color readDirectorEyeColor() {
        Integer eyeColorId;
        Color directorEyeColor = null;
        while (directorEyeColor == null) {
            System.out.println(TextColor.yellow(
                    """
                            Options:
                            1 RED
                            2 BLACK
                            3 BLUE
                            4 BROWN"""));
            eyeColorId = scanner.nextInt(TextColor.green("Eye color: "));
            if (eyeColorId != null)
                try {
                    directorEyeColor = (Color) new FieldHandler(
                            Color.values()[eyeColorId - 1],
                            NOT_NULL
                    ).handleObject();
                } catch (IndexOutOfBoundsException e) {
                    new WarningException("Выберите опцию 1-4").printMessage();
                } catch (NullValueException e) {
                    System.out.println(e.toString());
                }
        }
        return directorEyeColor;
    }

    public static String readLoginRegisterChoice() {
        String userChoice = null;
        while (!(userChoice != null && (userChoice.equals("") || userChoice.equals("1") || userChoice.equals("2")))) {
            System.out.println(TextColor.yellow("1 - Войти (по умолчанию)\n2 - Зарегистрироваться"));
            userChoice = scanner.nextLine(TextColor.green("Выберите опцию: "));
        }
        if (userChoice.equals(""))
            userChoice = "1";
        return userChoice;
    }

    public static UserCredentials readCredentials() {
        String username = null;
        String password = null;
        while (username == null || password == null || username.equals("") || password.equals("")) {
            username = scanner.nextLine("Логин: ");
            password = scanner.nextLine("Пароль: ");
        }
        return new UserCredentials(username, password);
    }
}
