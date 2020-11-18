package com.group11.cmpt276_project.view.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.ActivityRestuarantDetailBinding;
import com.group11.cmpt276_project.service.model.GPSCoordiantes;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.view.adapter.InspectionAdapter;
import com.group11.cmpt276_project.view.adapter.interfaces.IItemOnClickIndex;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.MainPageViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;

import java.util.List;

/**
 * This activity displays details of a specific restaurant
 */

public class RestaurantDetailActivity extends AppCompatActivity {

    private RestaurantsViewModel restaurantViewModel;
    private Restaurant restaurant;

    private String trackingNumber;

    private ActivityRestuarantDetailBinding binding;

    public static Intent startActivity(Context context, String trackingNumber) {
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra(Constants.TRACKING_NUMBER, trackingNumber);

        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = MainPageActivity.startActivity(this, null);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarant_detail);

        this.bind();
        this.setupReyclerView();
    }

    private void bind() {
        this.restaurantViewModel = RestaurantsViewModel.getInstance();

        this.trackingNumber = getIntent().getStringExtra(Constants.TRACKING_NUMBER);
        this.restaurant = restaurantViewModel.getByTrackingNumber(trackingNumber);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_restuarant_detail);
        this.binding.setRestaurant(restaurant);
        this.binding.setActivity(this);
    }

    private void setupReyclerView() {
        List<InspectionReport> inspectionReports = InspectionReportsViewModel.getInstance().getReports(restaurant.getTrackingNumber());

        if (inspectionReports.size() != 0) {
            InspectionAdapter adapter = new InspectionAdapter( inspectionReports, new InspectionOnClickTrackingNumber(trackingNumber));
            RecyclerView recyclerView = this.binding.resDetailRecycler;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void onCoordinateClicked(double latitude, double longitude) {

        GPSCoordiantes coordiantes = new GPSCoordiantes(latitude, longitude, this.trackingNumber);

        Intent intent = MainPageActivity.startActivity(this, coordiantes);
        MainPageViewModel.getInstance().setSelectedTabTab(0);
        startActivity(intent);
    }

    public void onBackClick() {
        Intent intent = MainPageActivity.startActivity(this, null);
        startActivity(intent);
    }


    private class InspectionOnClickTrackingNumber implements IItemOnClickIndex {

        private final String parent;

        public InspectionOnClickTrackingNumber(String trackingNUmber) {
            this.parent = trackingNUmber;
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = InspectionDetailActivity.startActivity(RestaurantDetailActivity.this, position, this.parent);
            startActivity(intent);
        }
    }
}

