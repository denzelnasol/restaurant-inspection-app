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
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.MainPageViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;
import com.group11.cmpt276_project.viewmodel.ViolationsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity displays details of a specific restaurant
 */

public class RestaurantDetailActivity extends AppCompatActivity {

    private RestaurantsViewModel restaurantViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;

    private String trackingNumber;

    private ActivityRestuarantDetailBinding binding;

    private Restaurant restaurant;

    public static Intent startActivity(Context context, String trackingNumber) {
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra(Constants.TRACKING_NUMBER, trackingNumber);

        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViolationsViewModel.getInstance().updateLanguage();
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
        this.observe();
    }

    private void bind() {
        this.restaurantViewModel = RestaurantsViewModel.getInstance();
        this.inspectionReportsViewModel = InspectionReportsViewModel.getInstance();

        this.trackingNumber = getIntent().getStringExtra(Constants.TRACKING_NUMBER);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_restuarant_detail);
        this.binding.setActivity(this);
    }

    private void observe() {
        this.restaurantViewModel.getRestaurants().observe(this, (data) -> {

            Restaurant restaurant = data.get(this.trackingNumber) == null ? this.restaurant : data.get(this.trackingNumber);

            if (restaurant != null) {
                this.restaurant = restaurant;
            }

            this.binding.setRestaurant(restaurant);
        });

        this.inspectionReportsViewModel.getReports().observe(this, (data) -> {
            this.setupRecyclerView(data.getOrDefault(this.trackingNumber, new ArrayList<>()));
        });
    }

    private void setupRecyclerView(List<InspectionReport> inspectionReports) {
        if (inspectionReports.size() != 0) {
            InspectionAdapter adapter = new InspectionAdapter(inspectionReports, new InspectionOnClickTrackingNumber(this.trackingNumber));
            RecyclerView recyclerView = this.binding.resDetailRecycler;
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void onCoordinateClicked(double latitude, double longitude) {

        GPSCoordiantes coordinates = new GPSCoordiantes(latitude, longitude, this.trackingNumber);

        Intent intent = MainPageActivity.startActivity(this, coordinates);
        MainPageViewModel.getInstance().setSelectedTab(0);
        startActivity(intent);
    }


    private class InspectionOnClickTrackingNumber implements InspectionAdapter.IInspectionItemOnClick {

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

    public void toggleFavouriteButton() {
        this.restaurant.setFavorite(!restaurant.isFavorite());
        this.restaurantViewModel.saveRestaurant(this.restaurant);
    }
}

