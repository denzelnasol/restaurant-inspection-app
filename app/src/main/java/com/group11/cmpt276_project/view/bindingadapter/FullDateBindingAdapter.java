package com.group11.cmpt276_project.view.bindingadapter;

import android.content.Context;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * A binding adapter that sets the full date of an inspection
 */
public class FullDateBindingAdapter {

    @BindingAdapter("fullDate")
    public static void setFullDateText(TextView textView, String date) {

        Locale locale = Constants.SUPPORTED_LANGUAGES_TO_LOCALE.getOrDefault(Locale.getDefault().getISO3Language(), Locale.ENGLISH);

        if(date == null || date.isEmpty()) {
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
        LocalDate inspectionDate = LocalDate.parse(Integer.valueOf(date).toString(), formatter);


        Context context = textView.getContext();

        String month = inspectionDate.getMonth().getDisplayName(TextStyle.SHORT, locale);
        int day = inspectionDate.getDayOfMonth();
        int year = inspectionDate.getYear();

        String fullDate = context.getString(R.string.full_date, month, day, year);

        textView.setText(fullDate);
    }
}
