package php.com.mk.grocerylist.utils;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Time and date formatter class.
 */
public class TimeDateHelper {
    private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    /**
     * Converts a string value into a date object
     *
     * @param value is the string which is to be converted to date.
     * @return a date object representing that string.
     */
    @TypeConverter
    public static Date fromTimestamp(String value) {
        if (value != null) {
            try {
                TimeZone timeZone = TimeZone.getDefault();
                df.setTimeZone(timeZone);
                return df.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * Converts a date value to string.
     *
     * @param value is the date which is to converted to string.
     * @return a string representing that date.
     */
    @TypeConverter
    public static String dateToTimestamp(Date value) {
        TimeZone timeZone = TimeZone.getDefault();
        df.setTimeZone(timeZone);
        return value == null ? null : df.format(value);
    }
}