package ui.locale;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public enum Lang {
    Canada(new Locale("en","CA")),
    Russian(new Locale("ru", "RU")),
    Belorussian( new Locale("ru", "BY"));

    private final Locale loc;

    Lang(Locale l) {
        loc = l;
    }

    String getDate(Date date) {
        return DateFormat.getDateInstance(DateFormat.FULL, loc).format(date);
    }

    String getCurrency(int number) {
        return NumberFormat.getCurrencyInstance(loc).format(number);
    }

    Locale getLocale() {
        return loc;
    }
}
