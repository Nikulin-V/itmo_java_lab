package classes.console;

import exceptions.WarningException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RepeatScanner {
    private final static Scanner scanner = new Scanner(System.in);

    public Long nextLong(String inputText) {
        Long value = null;
        boolean wasInput = false;
        while (!wasInput) {
            System.out.print(inputText);
            try {
                String inputValue = scanner.nextLine();
                if (!inputValue.isBlank())
                    value = Long.valueOf(inputValue);
                wasInput = true;
            } catch (NumberFormatException e) {
                new WarningException("Ввод должен быть числом (Long)").printMessage();
            }
        }
        return value;
    }

    public Double nextDouble(String inputText) {
        Double value = null;
        boolean wasInput = false;
        while (!wasInput) {
            System.out.print(inputText);
            try {
                String inputValue = scanner.nextLine();
                if (!inputValue.isBlank())
                    value = Double.valueOf(inputValue);
                wasInput = true;
            } catch (NumberFormatException e) {
                new WarningException("Ввод должен быть числом (Double)").printMessage();
            }
        }
        return value;
    }

    public Float nextFloat(String inputText) {
        Float value = null;
        boolean wasInput = false;
        while (!wasInput) {
            System.out.print(inputText);
            try {
                String inputValue = scanner.nextLine();
                if (!inputValue.isBlank())
                    value = Float.valueOf(inputValue);
                wasInput = true;
            } catch (NumberFormatException e) {
                new WarningException("Ввод должен быть числом (Float)").printMessage();
            }
        }
        return value;
    }

    public Integer nextInt(String inputText) {
        Integer value = null;
        boolean wasInput = false;
        while (!wasInput) {
            System.out.print(inputText);
            try {
                String inputValue = scanner.nextLine();
                if (!inputValue.isBlank())
                    value = Integer.valueOf(inputValue);
                wasInput = true;
            } catch (NumberFormatException e) {
                new WarningException("Ввод должен быть целым числом").printMessage();
            }
        }
        return value;
    }

    public String nextLine(String inputText) {
        String value = null;
        boolean wasInput = false;
        while (!wasInput) {
            System.out.print(inputText);
            try {
                value = scanner.nextLine();
                wasInput = true;
            } catch (InputMismatchException e) {
                new WarningException("Ввод должен быть непустой строкой").printMessage();
            }
        }
        return value;
    }
}
