package com.hyunjongkim.justtwo.a_lib;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TextView;

import java.util.Calendar;


public class DialogLib {
    public final String TAG = DialogLib.class.getSimpleName();
    private volatile static DialogLib instance;

    public static DialogLib getInstance() {
        if (instance == null) {
            synchronized (DialogLib.class) {
                if (instance == null) {
                    instance = new DialogLib();
                }
            }
        }

        return instance;
    }

    // Date Dialog
    public void setDatePicker(Context context, final TextView _dateDisplay) {
        int _year, _month, _day;
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        _year = c.get(Calendar.YEAR);
        _month = c.get(Calendar.MONTH);
        _day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                (view, year, monthOfYear, dayOfMonth) -> _dateDisplay.setText(year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日"), _year, _month, _day);

        datePickerDialog.show();
    }

    // Time Picker Dialog
    public void setTimePicker(Context context, final TextView _timeDisplay) {
        int mHour, mMinute;

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                (view, hourOfDay, minute) ->
                        _timeDisplay.setText(hourOfDay + "時" + minute + "分"), mHour, mMinute, false);

        timePickerDialog.show();
    }

}

