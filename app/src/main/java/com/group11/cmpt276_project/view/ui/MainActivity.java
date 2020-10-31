package com.group11.cmpt276_project.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.repository.InspectionReportRepository;
import com.group11.cmpt276_project.service.repository.RestaurantRepository;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;

public class MainActivity extends AppCompatActivity {

    private static int TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
        this.moveToRestaurantList();
    }

    private void moveToRestaurantList() {
        new Handler().postDelayed(() -> {
            Intent intent = RestaurantListActivity.startActivity(this);
            startActivity(intent);
        }, TIMEOUT);
    }

    private void init()  {
        RestaurantRepository restaurantRepository = new RestaurantRepository(getApplicationContext());
        RestaurantsViewModel.getInstance().init(restaurantRepository);
        InspectionReportRepository inspectionReportRepository = new InspectionReportRepository(getApplicationContext());
        InspectionReportsViewModel.getInstance().init(inspectionReportRepository);
    }


}