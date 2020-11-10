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

        if(Constants.SEVEN_ELEVEN.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_7eleven);
        } else if(Constants.AW.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_aw);
        } else if(Constants.BOOSTER_JUICE.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_booster_juice);
        } else if(Constants.BROWNS.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_browns);
        } else if(Constants.CHURCHS_CHICKEN.equals(restaurant)){
            imageView.setImageResource(R.drawable.res_ic_churchs_chicken);
        } else if(Constants.DAIRY_QUEEN.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_dairy_queen);
        } else if(Constants.DOMINOS.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_dominos);
        } else if(Constants.MCDONALDS.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_mcdonalds);
        } else if(Constants.PIZZAHUT.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_pizzahut);
        } else if(Constants.REAL_CANADIAN.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_real_canadian);
        } else if(Constants.STARBUCKS.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_starbucks);
        } else if(Constants.SUBWAY.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_subway);
        } else if(Constants.TIM_HORTONS.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_tim_hortons);
        } else if(Constants.WALMART.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_walmart);
        } else if(Constants.WENDYS.equals(restaurant)) {
            imageView.setImageResource(R.drawable.res_ic_wendys);
        } else{
            imageView.setImageResource(R.drawable.restaurant_detailed_img);
        }
    }
}
