package com.corelib.basic.global;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.corelib.R;
import com.corelib.basic.util.DateTimeUtility;
import com.corelib.basic.util.L;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Created by Abhijit on 21-Dec-16.
 */


public class DatePickerDialog {

    private static final int START_DATE = 1, END_DATE = 2;
    private DateSelectListener mListener;
    private DateSelectFlagListener mFlagListener;
    private int day, month, year;
    private int dateType;
    private Boolean minDate, maxDate;
    private long minDateTimeInMils;
    private Activity activity;

    /**
     * private int sDay, sMonth, sYear;
     * private int eDay, eMonth, eYear;
     * <p>
     * private void initDate() {
     * int[] date = DatePickerDialog2.initDate();
     * sDay=date[0];
     * sMonth=date[1];
     * sYear=date[2];
     * tvDate.setText(DatePickerDialog2.getFormattedDate(sDay,sMonth,sYear));
     * }
     */
    public static int[] initDate(String inputDate) {
        Calendar calendar;
        if (inputDate == null) {
            calendar = Calendar.getInstance();
        } else {
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy", Locale.US);
                Date date = format.parse(inputDate);
                calendar = Calendar.getInstance(TimeZone.getDefault());
                calendar.setTime(date);
            } catch (ParseException e) {
                L.m(e.toString());
                calendar = Calendar.getInstance();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new int[]{day, month, year};
    }

    public static String getFormattedDate(int day, int month, int year) {
        return formattedDate(day, month, year);
    }

    public interface DateSelectListener {
        void onSelectDateClick(int day, int month, int year, String ddMMMyy);
    }

    public interface DateSelectFlagListener {
        void onStartDateSelected(int day, int month, int year, String ddMMMyy);

        void onEndDateSelected(int day, int month, int year, String ddMMMyy);
    }

    public static DatePickerDialog newInstance(Activity activity, DateSelectListener listener, int day, int month, int year) {
        DatePickerDialog fragment = new DatePickerDialog();
        fragment.activity = activity;
        fragment.day = day;
        fragment.month = month;
        fragment.year = year;
        fragment.mListener = listener;
        fragment.minDate = false;
        fragment.maxDate = false;
        fragment.minDateTimeInMils = 0;
        return fragment;
    }

    public static DatePickerDialog newInstance(Activity activity, DateSelectListener listener, Boolean minDate, int day, int month, int year) {
        DatePickerDialog fragment = new DatePickerDialog();
        fragment.activity = activity;
        fragment.day = day;
        fragment.month = month;
        fragment.year = year;
        fragment.minDate = minDate;
        fragment.mListener = listener;
        fragment.maxDate = false;
        fragment.minDateTimeInMils = 0;
        return fragment;
    }

    public static DatePickerDialog newInstance(Activity activity, DateSelectFlagListener listener, Boolean minDate, int dateType, int day, int month, int year) {
        DatePickerDialog fragment = new DatePickerDialog();
        fragment.activity = activity;
        fragment.day = day;
        fragment.month = month;
        fragment.year = year;
        fragment.mFlagListener = listener;
        fragment.minDate = minDate;
        fragment.dateType = dateType;
        fragment.maxDate = false;
        fragment.minDateTimeInMils = 0;
        return fragment;
    }

    public static DatePickerDialog newInstance(Activity activity, DateSelectFlagListener listener, Boolean minDate, Boolean maxDate, long minDateTimeInMils, int dateType, int day, int month, int year) {
        DatePickerDialog fragment = new DatePickerDialog();
        fragment.activity = activity;
        fragment.day = day;
        fragment.month = month;
        fragment.year = year;
        fragment.mFlagListener = listener;
        fragment.minDate = minDate;
        fragment.maxDate = maxDate;
        fragment.dateType = dateType;
        fragment.minDateTimeInMils = minDateTimeInMils;
        return fragment;
    }

    public void show() {
        if (activity != null) {
            final Calendar calendar = Calendar.getInstance();
            if (day == 0) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            }
            android.app.DatePickerDialog dpd;
            dpd = new android.app.DatePickerDialog(activity, R.style.DatePicker, new android.app.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int yy, int mm, int dd) {
                    if (mListener != null) {
                        mListener.onSelectDateClick(dd, mm, yy, DateTimeUtility.cDateDDMMMYY(dd, mm, yy));
                    }
                    if (mFlagListener != null) {
                        if (dateType == START_DATE) {
                            mFlagListener.onStartDateSelected(dd, mm, yy, DateTimeUtility.cDateDDMMMYY(dd, mm, yy));
                        } else {
                            mFlagListener.onEndDateSelected(dd, mm, yy, DateTimeUtility.cDateDDMMMYY(dd, mm, yy));
                        }
                    }
                }
            }, year, month, day);


            if (minDate) {
                // Set the DatePicker minimum date selection to current date
                if (minDateTimeInMils != 0) {
//                dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());// get the current day
                    dpd.getDatePicker().setMinDate(minDateTimeInMils);// get the current day
                }
            }
            if (maxDate) {
                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());// get the current day
            }
            dpd.show();
        }
    }


    public static String formattedDate(int day, int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);

        return new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(cal.getTime());
    }
}