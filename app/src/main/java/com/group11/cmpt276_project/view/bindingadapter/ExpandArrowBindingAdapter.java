package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;

public class ExpandArrowBindingAdapter {

    @BindingAdapter("arrow")
    public static void setArrow(ImageView imageView, boolean isVisible){

        if(isVisible) {
            imageView.setImageResource(R.drawable.ic_baseline_arrow_drop_up);
        } else {
            imageView.setImageResource(R.drawable.ic_baseline_arrow_drop_down);
        }

    }
}
