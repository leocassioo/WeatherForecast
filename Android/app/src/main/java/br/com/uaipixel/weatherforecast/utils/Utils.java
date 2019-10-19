package br.com.uaipixel.weatherforecast.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Criado por  Leonardo Figueiredo em 23/02/19.
 */
public class Utils {

    public Utils() {
    }

    //converting kelvin to celsius
    public static double convertCelsius(double kelvin) {
        return kelvin - 273.0;
    }

    //converting kelvin to fahrenheit
    public double convertFahrenhiet(double kelvin) {
        return (convertCelsius(kelvin) * 9.0 / 5.0) + 32.0;
    }

    //receiving the date for handling
    public static String fullDate(Date dt) {
        int d = dt.getDate();
        int m = dt.getMonth() + 1;
        int a = dt.getYear() + 1900;

        Calendar date = new GregorianCalendar(a, m - 1, d);
        int ds = date.get(Calendar.DAY_OF_WEEK);

        return(dayOfWeek(ds, 1) + ", "+ monthName(m, 0) + " " +  d);
    }

    //day of the week
    private static String dayOfWeek(int i, int type) {
        String dayWeek[] = {"Sunday", "Monday", "Tuesday",
                "Wednesday", "Thursday", "Friday", "Saturday"};
        if (type == 0)
            return(dayWeek[i - 1]);
        else return(dayWeek[i - 1].substring(0, 3));
    }

    //month of the year
    private static String monthName(int i, int tipo) {
        String month[] = {"Jan", "Feb", "Mar", "Apr",
                "May", "Jun", "Jul", "Aug", "Sep", "Oct",
                "Nov", "Dec"};
        if (tipo == 0)
            return(month[i - 1]);
        else return(month[i - 1].substring(0, 3));
    }

    //checking for connection
    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
            return true;
        else {
            return false;
        }
    }

}
