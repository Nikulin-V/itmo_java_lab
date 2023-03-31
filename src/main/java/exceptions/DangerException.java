package exceptions;

import classes.console.TextColor;

public class DangerException extends Exception {
    private String message = "";

    public DangerException() {
    }

    public DangerException(String message) {
        this.message = message;
    }

    public void printMessage() {
        System.out.println(TextColor.red(getMessage()));
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return TextColor.red(getMessage());
    }
}
