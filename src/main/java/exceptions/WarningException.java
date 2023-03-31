package exceptions;

public class WarningException extends Exception {
    private static final String RESET = "\u001B[0m";
    private static final String YELLOW = "\u001B[33m";

    private String message = "";

    public WarningException() {
    }

    public WarningException(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(YELLOW + getMessage() + RESET);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return YELLOW + getMessage() + RESET;
    }
}
