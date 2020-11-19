package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;

import java.util.Map;

/**
 This class is a binding adapter to set the restaurant icon depending on the restaurant name.
 **/
public class RestaurantNameBindingAdapter {

    @BindingAdapter("restaurantIcon")
    public static void setRestaurantIcon(ImageView imageView, String restaurant){

        int resId = R.drawable.restaurant_detailed_img;

        for(Map.Entry<String, Integer> entry : Constants.RESTAURANT_ICON_MAP.entrySet()) {
            if (restaurant.toLowerCase().contains(entry.getKey().toLowerCase())) {
                resId = entry.getValue();
            }
        }

        imageView.setImageResource(resId);
    }
}
