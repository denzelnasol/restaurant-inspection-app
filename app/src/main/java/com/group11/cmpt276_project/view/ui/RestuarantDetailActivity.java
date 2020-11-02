package com.group11.cmpt276_project.view.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.view.adapter.RestaurantDetailAdapter;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;

import java.util.List;

public class RestuarantDetailActivity extends AppCompatActivity {

    private static final String INDEX = "index";


    private RestaurantsViewModel restaurant_viewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;
    private Restaurant restaurant;
    private RecyclerView recyclerView;
    private int[] hazardLevelIcon = {R.drawable.ic_launcher_background};

    public static Intent startActivity(Context context, int index) {
        Intent intent = new Intent(context, RestuarantDetailActivity.class);
        intent.putExtra(INDEX, index);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restuarant_detail);
        restaurant_viewModel = RestaurantsViewModel.getInstance();
        int index = getIntent().getIntExtra(INDEX, -1);
        restaurant = restaurant_viewModel.getByIndex(index);
        List<InspectionReport> inspectionReports = InspectionReportsViewModel.getInstance().getReports(restaurant.getTrackingNumber());
        setRestaurant_details();
        RestaurantDetailAdapter adapter = new RestaurantDetailAdapter(this, inspectionReports, hazardLevelIcon);
        recyclerView = findViewById(R.id.res_detail_recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setRestaurant_details() {
        TextView name = findViewById(R.id.res_detail_name);
        TextView address = findViewById(R.id.res_detail_address);
        TextView coordinates = findViewById(R.id.res_detail_coordinates);
        name.setText(restaurant.getName());
        address.setText(restaurant.getPhysicalAddress());
        coordinates.setText(restaurant.getLatitude() + ", " + restaurant.getLongitude());
    }

}