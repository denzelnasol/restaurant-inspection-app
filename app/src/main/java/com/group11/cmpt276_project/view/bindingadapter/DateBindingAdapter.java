package com.group11.cmpt276_project.view.bindingadapter;

import android.content.Context;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 This class is a binding adapter to set the date depending on the days since an inspection.
 **/
public class DateBindingAdapter {

    @BindingAdapter({"date", "show_last"})
    public static void setDateText(TextView textView, String date, boolean showLast) {

        if(date == null || date.isEmpty()) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate inspectionDate = LocalDate.parse(Integer.valueOf(date).toString(), formatter);
        LocalDate currentDate = LocalDate.now();



        Context context = textView.getContext();

        long daysSince = ChronoUnit.DAYS.between(inspectionDate, currentDate);
        int currentYear = currentDate.getYear();

        if(showLast) {
            formLast(textView, context, inspectionDate, daysSince, currentYear);
        } else {
            formatNormal(textView, context, inspectionDate, daysSince, currentYear);
        }


    }

    private static void formatNormal(TextView textView, Context context, LocalDate inspectionDate, long daysSince, int currentYear) {

        Locale locale = Constants.SUPPORTED_LANGUAGES_TO_LOCALE.getOrDefault(Locale.getDefault().getISO3Language(), Locale.ENGLISH);

        String month = inspectionDate.getMonth().getDisplayName(TextStyle.SHORT, locale);
        int year = inspectionDate.getYear();
        int day = inspectionDate.getDayOfMonth();

        String dateString;

        if(daysSince <= 30) {
            dateString = context.getString(R.string.days, daysSince);
        } else {
            dateString = context.getString(R.string.date, month, currentYear == year ? day : year);
        }

        textView.setText(dateString);
    }

    private static void formLast(TextView textView, Context context, LocalDate inspectionDate, long daysSince, int currentYear) {
        Locale locale = Constants.SUPPORTED_LANGUAGES_TO_LOCALE.getOrDefault(Locale.getDefault().getISO3Language(), Locale.ENGLISH);

        String sinceString;

        String month = inspectionDate.getMonth().getDisplayName(TextStyle.SHORT, locale);

        if(daysSince <= 30) {
            sinceString = context.getString(R.string.days, daysSince);
        } else if (inspectionDate.getYear() == currentYear) {
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
