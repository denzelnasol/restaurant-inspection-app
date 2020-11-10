package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.utils.Constants;
/**
 This class is a binding adapter to set the restaurant icon depending on the restaurant name.
 **/
public class RestaurantNameBindingAdapter {

    @BindingAdapter("restaurantIcon")
    public static void setRestaurantIcon(ImageView imageView, String restaurant){

        if (restaurant.contains(Constants.SEVEN_ELEVEN)) {
            imageView.setImageResource(R.drawable.res_ic_7eleven);
        } else if (restaurant.contains(Constants.AW)) {
            imageView.setImageResource(R.drawable.res_ic_aw);
        } else if (restaurant.contains(Constants.BOOSTER_JUICE)) {
            imageView.setImageResource(R.drawable.res_ic_booster_juice);
        } else if (restaurant.contains(Constants.BROWNS)) {
            imageView.setImageResource(R.drawable.res_ic_browns);
        } else if (restaurant.contains(Constants.CHURCHS_CHICKEN)) {
            imageView.setImageResource(R.drawable.res_ic_churchs_chicken);
        } else if (restaurant.contains(Constants.DAIRY_QUEEN)) {
            imageView.setImageResource(R.drawable.res_ic_dairy_queen);
        } else if (restaurant.contains(Constants.DOMINOS)) {
            imageView.setImageResource(R.drawable.res_ic_dominos);
        } else if (restaurant.contains(Constants.MCDONALDS)) {
            imageView.setImageResource(R.drawable.res_ic_mcdonalds);
        } else if (restaurant.contains(Constants.PIZZAHUT)) {
            imageView.setImageResource(R.drawable.res_ic_pizzahut);
        } else if (restaurant.contains(Constants.REAL_CANADIAN)) {
            imageView.setImageResource(R.drawable.res_ic_real_canadian);
        } else if (restaurant.contains(Constants.STARBUCKS)) {
            imageView.setImageResource(R.drawable.res_ic_starbucks);
        } else if (restaurant.contains(Constants.SUBWAY)) {
            imageView.setImageResource(R.drawable.res_ic_subway);
        } else if (restaurant.contains(Constants.TIM_HORTONS)) {
            imageView.setImageResource(R.drawable.res_ic_tim_hortons);
        } else if (restaurant.contains(Constants.WALMART)) {
            imageView.setImageResource(R.drawable.res_ic_walmart);
        } else if (restaurant.contains(Constants.WENDYS)) {
            imageView.setImageResource(R.drawable.res_ic_wendys);
        } else {
            imageView.setImageResource(R.drawable.restaurant_detailed_img);
        }
    }
}
