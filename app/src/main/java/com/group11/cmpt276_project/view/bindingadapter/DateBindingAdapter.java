package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DateBindingAdapter {

    @BindingAdapter("date")
    public static void setDateText(TextView textView, int date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime inspectionDate = LocalDateTime.parse(Integer.valueOf(date).toString(), formatter);



    }
}
