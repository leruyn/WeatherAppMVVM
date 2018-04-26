package com.example.leruyn.weatherappmvvm.utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by leruy on 4/26/2018.
 */

public class DateUtils {
    public static String formatTimeByIso8601UTC(long timeInMillis, Context context) {
        return formatTimeByIso8601UTC(getDateInMillis(timeInMillis), context);
    }

    public static String formatTimeByIso8601UTC(final Date date, Context context) {
        return formatTimeByIso8601UTC(date, context.getResources().getConfiguration().locale);
    }

    public static String formatTimeByIso8601UTC(final Date date, Locale locale) {
        String result = "";
        if (date != null) {
            if (locale == null) {
                locale = Locale.US;
            }
            SimpleDateFormat sdfIso8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
            sdfIso8601.setTimeZone(TimeZone.getTimeZone("UTC"));
            try {
                result = sdfIso8601.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static Date getDateInMillis(long timeInMillis1) {
        Calendar cal1 = Calendar.getInstance();
        if (timeInMillis1 > 0) {
            cal1.setTimeInMillis(timeInMillis1);
        }
        return cal1.getTime();
    }
}
