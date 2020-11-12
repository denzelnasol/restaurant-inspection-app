package com.group11.cmpt276_project.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.repository.impl.JsonInspectionReportRepository;
import com.group11.cmpt276_project.service.repository.impl.JsonRestaurantRepository;
import com.group11.cmpt276_project.service.repository.impl.JsonViolationRepository;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;
import com.group11.cmpt276_project.viewmodel.ViolationsViewModel;

/**
 * This activity displays a welcome screen prior to showing the restaurant list
 */
public class MainActivity extends AppCompatActivity {

    private static final int TIMEOUT = 3200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.init();
        this.moveToRestaurantList();
    }

    private void moveToRestaurantList() {
        new Handler().postDelayed(() -> {
            Intent intent = MainPageActivity.startActivity(this, false, null);
            startActivity(intent);
        }, TIMEOUT);
    }

    private void init()  {
        JsonRestaurantRepository jsonRestaurantRepository = new JsonRestaurantRepository(getApplicationContext());
        RestaurantsViewModel.getInstance().init(jsonRestaurantRepository);
        JsonInspectionReportRepository jsonInspectionReportRepository = new JsonInspectionReportRepository(getApplicationContext());
        InspectionReportsViewModel.getInstance().init(jsonInspectionReportRepository);
        JsonViolationRepository jsonViolationRepository = new JsonViolationRepository(getApplicationContext());
        ViolationsViewModel.getInstance().init(jsonViolationRepository);
    }


}