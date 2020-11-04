package com.group11.cmpt276_project.view.bindingadapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.viewmodel.ViolationsViewModel;

// A binding adapter to set the violation category icon
public class ViolationCategoryBindingAdapter {

    @BindingAdapter("category")
    public static void setViolationCategoryImage(ImageView imageView, String stringId){

        if(stringId == null || stringId.isEmpty()) {
            return;
        }

        //int id = Integer.parseInt(stringId);

        if (stringId.startsWith("1")) {
            imageView.setImageResource(R.drawable.ic_vegetables);
        }
        else if (stringId.startsWith("2")) {
            imageView.setImageResource(R.drawable.ic_dirty);
        }
        else if (stringId.startsWith("3")) {
            imageView.setImageResource(R.drawable.ic_house);
        }
        else if (stringId.startsWith("4")) {
            imageView.setImageResource(R.drawable.ic_barber);
        }
        else {
            imageView.setImageResource(R.drawable.ic_cross);
        }

    }
}
