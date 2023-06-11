package ui.locale;

import io.github.cdimascio.dotenv.Dotenv;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.*;

public class Lang {
    private ResourceBundle resourceBundle;
    private Locale currentLocale;
    private static final Map<String, Locale> locales = new HashMap<>() {
        {
            put("EN", new Locale("en", "CA"));
            put("RU", new Locale("ru", "RU"));
            put("BY", new Locale("ru", "BY"));
        }
    };
    //TODO need to translate even jcomboboxes to current language -> rewrite
    private static final String[] availableLanguages = {"Русский", "Канадский", "Белорусский"};


    public Lang() {
        Dotenv env = Dotenv.load();
        this.currentLocale = locales.get(env.get("GUI_LANG", "RU"));
        this.resourceBundle = ResourceBundle.getBundle("ui.locale.ResourceBundle",
                currentLocale);
    }

    String getDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.FULL, currentLocale).format(date);
    }

    String getCurrency(int number) {
        return NumberFormat.getCurrencyInstance(currentLocale).format(number);
    }

    public String getString(String tag) {
        return resourceBundle.getString(tag);
    }

    public static String[] getAvailableLanguagesList() {
        return availableLanguages;
    }

    public void setLanguage(String tag) {
        this.currentLocale = locales.get(tag);
        resourceBundle = ResourceBundle.getBundle("ui.locale.ResourceBundle",
                currentLocale);
        //TODO - how to update every labels in time?
    }

    Locale getCurrentLocale() {
        return currentLocale;
    }


}
