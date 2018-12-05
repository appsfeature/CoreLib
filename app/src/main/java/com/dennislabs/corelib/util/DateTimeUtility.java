package com.dennislabs.corelib.util;

import android.text.format.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@SuppressWarnings("all")
public class DateTimeUtility {

    // use in adapter Class
    private CharSequence converteTimestamp(String mileSegundos){
        return DateUtils.getRelativeTimeSpanString(Long.parseLong(mileSegundos), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);
    }

    public static String convertTime24to12Hours(String inputTime) {
        DateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(inputTime);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException | NullPointerException e) {
            L.m(e.toString());
        }
        return outputDateStr;
    }


    /**
     * @Usage used in DatePickerDialog
     */
    public static String cDateDDMMMYY(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        return new SimpleDateFormat("dd-MMM-yy", Locale.US).format(cal.getTime());
    }


    public static String convertTime12to24Hours(String inputTime) {
        DateFormat inputFormat = new SimpleDateFormat("hh:mm a", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.US);
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(inputTime);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            L.m(e.toString());
        }
        return outputDateStr;
    }


    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public static float parseFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }


    public static String convertDateMMM(String inputDate) {
        DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM", Locale.US);
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(inputDate);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            L.m(e.toString());
        }
        return outputDateStr;
    }

    /**
     *
     * @param inputDate same as input pattern
     * @param inputPattern like dd/MM/yyyy
     * @param outputPattern like dd-MMM-yyyy
     * @return outputFormattedDate
     */
    public static String convertDate(String inputDate, String inputPattern, String outputPattern) {
        DateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.US);
        DateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.US);
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(inputDate);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            L.m(e.toString());
        }
        return outputDateStr;
    }


    public static String convertDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
//		int numDays = cal.getActualMaximum(Calendar.DATE);
        return new SimpleDateFormat("d MMMM yyyy, EEEE", Locale.US).format(cal.getTime());
    }

    /**
     * @param outputPattern like dd-MMM-yy
     * @return formatted date
     */
    public static String convertDate(int day, int month, int year, String outputPattern) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        return new SimpleDateFormat(outputPattern, Locale.US).format(cal.getTime());
    }



    public static String convertDateTimeStamp(int year, int month, int day, int hour, int min) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        return new SimpleDateFormat("yyyyMMddHHmm", Locale.US).format(cal.getTime());
    }

    public static String convertDateTimeStamp(String inputDate, int hour, int min) {
        DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("yyyyMMdd", Locale.US);
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(inputDate);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException | NullPointerException e) {
            L.m(e.toString());
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);

        String format = new SimpleDateFormat("HHmm", Locale.US).format(cal.getTime());

        return outputDateStr+format;
    }

    public static String convertDateTimeStamp(String inputDate, String time) {
        DateFormat inputFormat = new SimpleDateFormat("dd-MMM-yyhh:mm a", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("yyyyMMddHHmm", Locale.US);
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(inputDate+time);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException | NullPointerException e) {
            L.m(e.toString());
        }
        return outputDateStr;
    }

    public static String convertTimeTo24Hours(int hour, int min, String AMPM) {
        String startTime = String.valueOf(hour) + ":" + String.valueOf(min) + ":" + AMPM.toLowerCase();
        DateFormat inputFormat = new SimpleDateFormat("hh:mm:a", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.US);
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(startTime);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException e) {
            L.m(e.toString());
        }
        return outputDateStr;
    }

    public void getDateTime() {
        String date;
        final Calendar cal = Calendar.getInstance();
//		syear = cal.get(Calendar.YEAR);
//		smonth = cal.get(Calendar.MONTH);
//		sday = cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int generateTimeStamp(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        String format = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(cal.getTime());
        String[] tempDate = format.split("-");
        String timeStamp = String.valueOf(tempDate[2]) + String.valueOf(tempDate[1]) + String.valueOf(tempDate[0]);
        return Integer.parseInt(timeStamp);
    }


    public static String getTimeStamp() {
        return new SimpleDateFormat("_yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    public static String getDateStampFormatted() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public static int[] getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        return new int[]{day, month, year};
    }

    public static String getDateTimeStampFormatted() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
    }


    public static String getTimeStampFormatted() {
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    public static String convertServerDate(String inputDate) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy", Locale.US);
        Date date;
        String outputDateStr = "";
        try {
            date = inputFormat.parse(inputDate);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException | NullPointerException e) {
            L.m(e.toString());
        }
        return outputDateStr;
    }

    public static String convertServerDateTime(String inputDate) {
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yy hh:mm:a", Locale.US);
        Date date;
        String outputDateStr;
        try {
            date = inputFormat.parse(inputDate);
            outputDateStr = outputFormat.format(date);
        } catch (ParseException | NullPointerException e) {
            L.m(e.toString());
            outputDateStr="Not available";
        }
        return outputDateStr;
    }

    public static String getYearMonth() {
        return new SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(new Date());
    }

    public static int[] getDayMonthYear(String inputDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.US);
        Date date;
        try {
            date = format.parse(inputDate);
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
            calendar.setTime(date);
            int sYear = calendar.get(Calendar.YEAR);
            int sMonth = calendar.get(Calendar.MONTH);
            int sDay = calendar.get(Calendar.DAY_OF_MONTH);
            return new int[]{sDay, sMonth, sYear};
        } catch (ParseException e) {
            L.m(e.toString());
            return new int[]{0, 0, 0};
        }
    }

}
