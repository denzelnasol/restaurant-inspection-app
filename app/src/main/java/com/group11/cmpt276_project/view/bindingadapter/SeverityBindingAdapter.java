package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;

public class SeverityBindingAdapter {

    @BindingAdapter("severity")
    public static void setSeverityImage(ImageView imageView, String severity){

        if(severity == null || severity.isEmpty()) {
            return;
        }

        if(Constants.CRITICAL.equals(severity)) {
            imageView.setImageResource(R.drawable.ic_biohazard);
        } else {
            imageView.setImageResource(R.drawable.ic_warning);
        }

    }
}
