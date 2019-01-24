package com.corelib.basic.global;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

/**
 * @author Created by Abhijit on 21-Dec-16.
 */
public class TimePickerDialog extends DialogFragment implements android.app.TimePickerDialog.OnTimeSetListener {

    public static final int TIME_START = 1,TIME_END=2;
    private TimeSelectListener mListener;
    private TimeSelectTypeListener mFlagListener;
    private int hour, min;
    private int timeType;
    private Boolean is24HourView;


    public interface TimeSelectListener {
        void onSelectDateClick(DialogFragment dialog, int hour, int min, String formattedTime);
    }

    public interface TimeSelectTypeListener {
        void onStartTimeSelected(DialogFragment dialog, int hour, int min, String formattedTime);

        void onEndTImeSelected(DialogFragment dialog, int hour, int min, String formattedTime);
    }

    public static TimePickerDialog newInstance(TimeSelectListener listener, Boolean is24HourView, int hour, int min) {
        TimePickerDialog fragment = new TimePickerDialog();
        fragment.hour=hour;
        fragment.min=min;
        fragment.is24HourView=is24HourView;
        fragment.mListener = listener;
        return fragment;
    }

    public static TimePickerDialog newInstance(TimeSelectTypeListener listener, Boolean is24HourView, int timeType, int hour, int min) {
        TimePickerDialog fragment = new TimePickerDialog();
        fragment.hour=hour;
        fragment.min=min;
        fragment.is24HourView=is24HourView;
        fragment.mFlagListener = listener;
        fragment.timeType=timeType;
        return fragment;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        android.app.TimePickerDialog dpd;
        dpd = new android.app.TimePickerDialog(getActivity(), this, hour, min, is24HourView);
        return dpd;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(mListener!=null) {
            mListener.onSelectDateClick(TimePickerDialog.this, hourOfDay, minute, getTime(hourOfDay,minute));
        }
        if(mFlagListener!=null){
            if (timeType == TIME_START) {
                mFlagListener.onStartTimeSelected(TimePickerDialog.this, hourOfDay, minute, getTime(hourOfDay,minute));
            } else {
                mFlagListener.onEndTImeSelected(TimePickerDialog.this, hourOfDay, minute, getTime(hourOfDay,minute));
            }
        }
    }

    private String getTime(int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        String format;
        if(is24HourView) {
            format = new SimpleDateFormat("HH:mm:ss").format(c.getTime());
        }else{
            format = new SimpleDateFormat("hh:mm a").format(c.getTime());
        }
        return format;
    }


//    @Override
//    public void onDateSet(DatePicker view, int yy, int mm, int dd) {
//        if(mListener!=null) {
//            mListener.onSelectDateClick(TimePickerDialog.this, dd, mm, yy);
//        }
//        if(mFlagListener!=null){
//            mFlagListener.onSelectDateFlagClick(TimePickerDialog.this,timeType, dd, mm, yy);
//        }
//    }
}