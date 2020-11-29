package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;
/**
 This class is a binding adapter to set the hazard image depending on the hazard.
 **/
public class HazardRatingBindingAdapter {

    @BindingAdapter("hazardIcon")
    public static void setHazardImage(ImageView imageView, String hazard){

        if(hazard == null || hazard.isEmpty()) {
            return;
        }


        int resId = 0;

        if(Constants.LOW.equals(hazard)) {
            resId = R.drawable.ic_smiling;
        } else if(Constants.MODERATE.equals(hazard)) {
            resId = R.drawable.ic_neutral;
        } else {
            resId = R.drawable.ic_sad;
        }

        imageView.setImageResource(resId);
    }

    @BindingAdapter("hazardText")
    public static void setHazardText(TextView textView, String hazard) {
        if(hazard == null || hazard.isEmpty()) {
            return;
        }

        textView.setText(hazard);

        int color = 0;

        if(hazard.contains(Constants.LOW)) {
            color = R.color.colorLow;
        } else if(hazard.contains(Constants.MODERATE)) {
            color = R.color.colorMedium;
        } else {
            color = R.color.colorHigh;
        }

        textView.setTextColor(textView.getContext().getColor(color));
    }
 }
