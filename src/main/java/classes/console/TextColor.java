package classes.console;

public enum TextColor {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    GREY("\u001B[37m"),
    BG_BLACK("\u001B[40m"),
    BG_RED("\u001B[41m"),
    BG_GREEN("\u001B[42m"),
    BG_YELLOW("\u001B[43m"),
    BG_BLUE("\u001B[44m"),
    BG_PURPLE("\u001B[45m"),
    BG_CYAN("\u001B[46m"),
    BG_GREY("\u001B[47m");

    private final String value;

    TextColor(String value) {
        this.value = value;
    }

    public static String color(String s, TextColor color) {
        return color + s + RESET;
    }

    public static String red(String s) {
        return RED + s + RESET;
    }

    public static String green(String s) {
        return GREEN + s + RESET;
    }

    public static String yellow(String s) {
        return YELLOW + s + RESET;
    }

    public static String blue(String s) {
        return BLUE + s + RESET;
    }

    public static String purple(String s) {
        return PURPLE + s + RESET;
    }

    public static String cyan(String s) {
        return CYAN + s + RESET;
    }

    public static String grey(String s) {
        return GREY + s + RESET;
    }

    @Override
    public String toString() {
        return value;
    }
}
