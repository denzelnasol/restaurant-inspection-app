package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;

public class FavouriteIconBindingAdapter {
    @BindingAdapter("app:favoriteIcon")
    public static void setFavoriteIcon(ImageView imageView, boolean isFavourite){

        int resId = 0;

        if(isFavourite) {
            resId = android.R.drawable.star_big_on;
        } else {
            resId = 0;
        }

        imageView.setImageResource(resId);
    }

    @BindingAdapter("app:setFavoriteButton")
    public static void setFavoriteButton(ImageButton imageButton, boolean isFavourite){

        int resId = 0;

        if(isFavourite) {
            resId = android.R.drawable.star_big_on;
        } else {
            resId = android.R.drawable.star_big_off;
        }

        imageButton.setImageResource(resId);
    }
}
