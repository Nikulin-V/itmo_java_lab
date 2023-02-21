package exceptions;

public class WarningException extends Exception {
    private static final String COLOR_RESET = "\u001B[0m";
    private static final String COLOR_YELLOW = "\u001B[33m";

    private String message = "";

    public WarningException() {
    }

    public WarningException(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(COLOR_YELLOW + getMessage() + COLOR_RESET);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return COLOR_YELLOW + getMessage() + COLOR_RESET;
    }
}
