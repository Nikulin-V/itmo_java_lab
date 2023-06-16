package ui.locale;

import io.github.cdimascio.dotenv.Dotenv;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.*;

public class Lang {
    private ResourceBundle resourceBundle;
    private Locale currentLocale;
    private String currentLocaleTag;
    private static final Map<String, Locale> locales = new HashMap<>() {
        {
            put("EN", new Locale("en", "CA"));
            put("RU", new Locale("ru", "RU"));
            put("BY", new Locale("ru", "BY"));
        }
    };
    private static final ArrayList<String> indexToLanguageTag = new ArrayList<>() {
        {
            add("EN");
            add("RU");
            add("BY");
        }
    };
    //TODO need to translate even jcomboboxes to current language -> rewrite
    private static final String[] availableLanguages = {"Русский", "Канадский", "Белорусский"};


    public Lang() {
        Dotenv env = Dotenv.load();
        this.currentLocaleTag = env.get("GUI_LANG", "RU");
        this.currentLocale = locales.get(currentLocaleTag);
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

    public String[] getTableColumns() {
        return new String[]{
                getString("table_name"),
                getString("table_coordinates"),
                getString("table_creation_date"),
                getString("table_count_oscars"),
                getString("table_count_golden_palms"),
                getString("table_budget"),
                getString("table_mpaarating"),
                getString("table_creator")};
    }

    public void setLanguage(int index) {
        this.currentLocale = locales.get(indexToLanguageTag.get(index));
        this.currentLocaleTag = indexToLanguageTag.get(index);
        resourceBundle = ResourceBundle.getBundle("ui.locale.ResourceBundle",
                currentLocale);
        //TODO - how to update every labels in time?
    }

    public int getCurrentLocaleIndex() {
        return indexToLanguageTag.indexOf(currentLocaleTag);
    }


}
