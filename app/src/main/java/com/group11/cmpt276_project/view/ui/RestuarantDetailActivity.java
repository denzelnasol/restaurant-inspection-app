package com.group11.cmpt276_project.view.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.ActivityRestuarantDetailBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.view.adapter.InspectionAdapter;
import com.group11.cmpt276_project.view.adapter.interfaces.IItemOnClick;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;

import java.util.List;

/**
 * This activity displays details of a specific restaurant
 */

public class RestuarantDetailActivity extends AppCompatActivity {

    private static final String INDEX = "index";

    private RestaurantsViewModel restaurantViewModel;
    private Restaurant restaurant;

    private int index;

    public static Intent startActivity(Context context, int index) {
        Intent intent = new Intent(context, RestuarantDetailActivity.class);
        intent.putExtra(INDEX, index);
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = RestaurantListActivity.startActivity(this);
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

        this.index = getIntent().getIntExtra(INDEX, -1);
        this.restaurant = restaurantViewModel.getByIndex(index);

        ActivityRestuarantDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_restuarant_detail);
        binding.setRestaurant(restaurant);
        binding.setRestaurantDetailActivity(this);
    }

    private void setupReyclerView() {
        List<InspectionReport> inspectionReports = InspectionReportsViewModel.getInstance().getReports(restaurant.getTrackingNumber());

        if (inspectionReports.size() != 0) {
            InspectionAdapter adapter = new InspectionAdapter( inspectionReports, new InspectionOnClick(index));
            RecyclerView recyclerView = findViewById(R.id.res_detail_recycler);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }



    private class InspectionOnClick implements IItemOnClick{

        private int parent;

        public InspectionOnClick(int index) {
            this.parent = index;
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = InspectionDetailActivity.startActivity(RestuarantDetailActivity.this, position, this.parent);
            startActivity(intent);
        }
    }
}

