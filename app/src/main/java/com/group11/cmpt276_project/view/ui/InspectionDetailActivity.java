package com.group11.cmpt276_project.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.ActivityInspectionDetailBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;
import com.group11.cmpt276_project.viewmodel.ViolationsViewModel;

public class InspectionDetailActivity extends AppCompatActivity {

    private final static String INDEX = "index";
    private final static String PARENT = "parent";

    public static Intent startActivity(Context context, int index, int parent) {
        Intent intent = new Intent(context, InspectionDetailActivity.class);
        intent.putExtra(INDEX, index);
        intent.putExtra(PARENT, parent);

        return intent;
    }

    private int parent;
    private int index;
    private ViolationsViewModel violationsViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;
    private RestaurantsViewModel restaurantsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bind();
    }

    public void onBackClicked() {
        Intent intent = RestuarantDetailActivity.startActivity(this, this.parent);
        startActivity(intent);
    }

    private void bind() {
        Intent intent = getIntent();
        this.index = intent.getIntExtra(INDEX, -1);
        this.parent = intent.getIntExtra(PARENT, -1);

        this.violationsViewModel = ViolationsViewModel.getInstance();
        this.inspectionReportsViewModel = InspectionReportsViewModel.getInstance();
        this.restaurantsViewModel = RestaurantsViewModel.getInstance();

        String trackingNumber = this.restaurantsViewModel.getByIndex(parent).getTrackingNumber();

        InspectionReport inspectionReport = this.inspectionReportsViewModel.getByIndexAndTrackingNumbe(trackingNumber, index);

        ActivityInspectionDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_detail);
        binding.setReport(inspectionReport);
        binding.setActivity(this);
    };
}