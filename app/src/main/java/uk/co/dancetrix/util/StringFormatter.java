package uk.co.dancetrix.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class StringFormatter {

    private static final SimpleDateFormat DATE_FORMAT;
    private static final SimpleDateFormat DATE_TIME_FORMAT;
    private static final NumberFormat CURRENCY_FORMAT;

    static {
        DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
        DATE_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.UK);
        CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
        CURRENCY_FORMAT.setCurrency(Currency.getInstance("GBP"));
    }

    public static String formatDate(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static String formatDateTime(Date date) {
        return DATE_TIME_FORMAT.format(date);
    }

    public static String formatCurrency(Double amount) {
        return CURRENCY_FORMAT.format(amount);
    }

}
