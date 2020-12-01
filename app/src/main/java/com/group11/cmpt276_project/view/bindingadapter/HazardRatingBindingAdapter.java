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


        int resId = R.drawable.ic_smiling;

        if(Constants.MODERATE.equals(hazard)) {
            resId = R.drawable.ic_neutral;
        } else if(Constants.HIGH.equals(hazard)) {
            resId = R.drawable.ic_sad;
        }

        imageView.setImageResource(resId);
    }

    @BindingAdapter("hazardText")
    public static void setHazardText(TextView textView, String hazard) {
        if(hazard == null || hazard.isEmpty()) {
            return;
        }

        int translatedLevelId = R.string.low;
        int color = R.color.colorLow;

        String toReplace = Constants.LOW;

         if(hazard.contains(Constants.MODERATE)) {
            color = R.color.colorMedium;
             translatedLevelId = R.string.moderate;
             toReplace = Constants.MODERATE;
        } else if(hazard.contains(Constants.HIGH)){
            color = R.color.colorHigh;
             translatedLevelId = R.string.high;
             toReplace = Constants.HIGH;
        }

        textView.getContext().getString(translatedLevelId);

        textView.setText(hazard.replace(toReplace, textView.getContext().getString(translatedLevelId)));
        textView.setTextColor(textView.getContext().getColor(color));
    }
 }
