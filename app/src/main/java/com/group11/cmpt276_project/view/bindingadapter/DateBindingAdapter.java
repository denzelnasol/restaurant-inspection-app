package com.group11.cmpt276_project.view.bindingadapter;

import android.content.Context;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;


public class DateBindingAdapter {

    @BindingAdapter("date")
    public static void setDateText(TextView textView, String date) {

        if(date == null || date.isEmpty()) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate inspectionDate = LocalDate.parse(Integer.valueOf(date).toString(), formatter);
        LocalDate currentDate = LocalDate.now();

        long daysSince = ChronoUnit.DAYS.between(inspectionDate, currentDate);

        Context context = textView.getContext();

        String month = inspectionDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.CANADA);

        String sinceString =  "";

        if(daysSince <= 30) {
             sinceString = context.getString(R.string.days, daysSince);
        } else if (inspectionDate.getYear() == currentDate.getYear()) {
            int day = inspectionDate.getDayOfMonth();
            sinceString = context.getString(R.string.month, month, day);
        } else {
            int year = inspectionDate.getYear();
            sinceString = context.getString(R.string.year, month, year);
        }


        String lastInspectionString = context.getString(R.string.last_inspect, sinceString);

        textView.setText(lastInspectionString);
    }
}
