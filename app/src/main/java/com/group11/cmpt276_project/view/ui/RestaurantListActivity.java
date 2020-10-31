package com.group11.cmpt276_project.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.view.adapter.RestaurantAdapter;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;

public class RestaurantListActivity extends AppCompatActivity {

    private RestaurantsViewModel restaurantsViewModel;
    private RecyclerView recyclerView;

    public static Intent startActivity(Context context) {
        return new Intent(context, RestaurantListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);
        this.bind();
        this.observeRestaurants();
    }

    private void bind() {
        this.restaurantsViewModel = RestaurantsViewModel.getInstance();

        this.recyclerView = findViewById(R.id.restaurant_list);
    }

    private void observeRestaurants() {
        this.restaurantsViewModel.get().observe(this, (data) -> {
            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(data);

            this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            this.recyclerView.setAdapter(restaurantAdapter);
        });
    }
}