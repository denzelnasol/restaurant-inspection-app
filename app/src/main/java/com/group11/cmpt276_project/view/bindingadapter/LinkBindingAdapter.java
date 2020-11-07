package com.group11.cmpt276_project.view.bindingadapter;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

public class LinkBindingAdapter {

    @BindingAdapter("link")
    public static void setSeverityImage(TextView textView, String link){
        if(link == null || link.isEmpty()) {
            return;
        }

        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(link);
        ssb.setSpan(new URLSpan("#"), 0, ssb.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(ssb, TextView.BufferType.SPANNABLE);
    }
}
