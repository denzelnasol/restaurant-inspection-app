package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;

public class HazardRatingBindingAdapter {

    @BindingAdapter("hazard")
    public static void setHazardImage(ImageView imageView, String hazard){

        if(hazard == null || hazard.isEmpty()) {
            return;
        }

        if(Constants.LOW.equals(hazard)) {
            imageView.setImageResource(R.drawable.ic_smiling);
        } else if(Constants.MODERATE.equals(hazard)) {
            imageView.setImageResource(R.drawable.ic_neutral);
        } else {
            imageView.setImageResource(R.drawable.ic_sad);
        }
    }
}
