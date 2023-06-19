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
            put("HUN", new Locale("hu", "HU"));
        }
    };

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
        this.currentLocale = locales.get(Languages.getTag(index));
        this.currentLocaleTag = Languages.getTag(index);
        resourceBundle = ResourceBundle.getBundle("ui.locale.ResourceBundle",
                currentLocale);
    }

    public Locale getCurrentLocale() {
        return currentLocale;
    }
    public int getCurrentLocaleIndex() {
        return Languages.getIndex(currentLocaleTag);
    }
    public String getCurrentLocaleTag() {
        return currentLocaleTag;
    }
    public String changeNumberFormat(int number) {
        return NumberFormat.getCurrencyInstance(currentLocale).format(number);
    }
    public String changeDateFormat(int number) {
        return NumberFormat.getCurrencyInstance(currentLocale).format(number);
    }
}
