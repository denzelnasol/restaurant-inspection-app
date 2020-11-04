package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;


public class ViolationCategoryBindingAdapter {

    @BindingAdapter("category")
    public static void setSeverityImage(ImageView imageView, String stringId){

        if(stringId == null || stringId.isEmpty()) {
            return;
        }

        int id = Integer.parseInt(stringId);


    }
}
