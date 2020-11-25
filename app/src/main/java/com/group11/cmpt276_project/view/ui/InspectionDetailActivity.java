package com.group11.cmpt276_project.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.ActivityInspectionDetailBinding;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.view.adapter.ViolationAdapter;
import com.group11.cmpt276_project.viewmodel.InspectionReportDetailViewModel;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.ViolationsViewModel;
import com.group11.cmpt276_project.viewmodel.factory.InspectionReportDetailViewModelFactory;

import java.util.List;
/**
 This is an activity for the InspectionDetail.
 It allow users to see list of violations using RecyclerView.
 **/

public class InspectionDetailActivity extends AppCompatActivity {


    public static Intent startActivity(Context context, int index, String parent) {
        Intent intent = new Intent(context, InspectionDetailActivity.class);
        intent.putExtra(Constants.INDEX, index);
        intent.putExtra(Constants.PARENT, parent);

        return intent;
    }

    private String parent;
    private int index;

    private ViolationsViewModel violationsViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;
    private InspectionReportDetailViewModel inspectionReportDetailViewModel;

    private RecyclerView recyclerView;


    private ActivityInspectionDetailBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bind();
        this.observe();
    }

    public void onBackClicked() {
       finish();
    }

    private void bind() {
        Intent intent = getIntent();
        this.index = intent.getIntExtra(Constants.INDEX, -1);
        this.parent = intent.getStringExtra(Constants.PARENT);

        this.violationsViewModel = ViolationsViewModel.getInstance();
        this.inspectionReportsViewModel = InspectionReportsViewModel.getInstance();

        InspectionReportDetailViewModelFactory inspectionReportDetailViewModelFactory = new InspectionReportDetailViewModelFactory(
                this.inspectionReportsViewModel.getReports(),
                this.violationsViewModel.getViolations(),
                this.parent,
                this.index
        );
        this.inspectionReportDetailViewModel = new ViewModelProvider(this, inspectionReportDetailViewModelFactory).get(InspectionReportDetailViewModel.class);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_inspection_detail);
        this.binding.setActivity(this);

        this.recyclerView = this.binding.violationList;
    }

    private void observe() {

        this.inspectionReportsViewModel.getReports().observe(this, (data) -> {
            this.binding.setReport(data.get(this.parent).get(this.index));
        });

        this.inspectionReportDetailViewModel.getData().observe(this, (data) -> {
            ViolationAdapter violationAdapter = new ViolationAdapter(data.second, data.first, new ViolationItemOnClickTrackingNumber());
            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(violationAdapter);
        });
    }

    private class ViolationItemOnClickTrackingNumber implements ViolationAdapter.IViolationItemOnClick {

        @Override
        public void onItemClick(int position) {
            inspectionReportDetailViewModel.setIsVisible(position);
        }
    }
}