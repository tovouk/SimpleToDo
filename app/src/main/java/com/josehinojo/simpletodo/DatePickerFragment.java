package com.josehinojo.simpletodo;
/*
Credit: https://www.codeproject.com/Articles/1112812/Android-Alert-Dialog-Tutorial-Working-with-Time-Pi

 */

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {
    private DatePicker datePicker;

    public interface DateDialogListener{
        void onFinishDialog(Date date);
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstance){

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.datepicker,null);
        datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        return new android.support.v7.app.AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = datePicker.getYear();
                                int mon = datePicker.getMonth();
                                int day = datePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year,mon,day).getTime();
                                DateDialogListener activity = (DateDialogListener) getActivity();
                                activity.onFinishDialog(date);
                                dismiss();
                            }
                        })
                .create();
    }

}
