package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.RestaurantItemBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.view.adapter.interfaces.IItemOnClickTrackingNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 This class is an adapter to hook the RecyclerView with DataBinding for the restaurant list.
 **/
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private final Map<String,Restaurant> restaurants;
    private final List<String> trackingNumbers;
    private final Map<String, InspectionReport> inspectionReports;
    private final IItemOnClickTrackingNumber onRestaurantItemClick;

    public RestaurantAdapter(Map<String,Restaurant> restaurants, Map<String, InspectionReport> inspectionReports, IItemOnClickTrackingNumber onRestaurantItemClick) {
        this.restaurants = restaurants;
        this.trackingNumbers = new ArrayList<>(restaurants.keySet());

        Collections.sort(this.trackingNumbers, (String a, String b) -> {
            Restaurant aRestaurant = restaurants.get(a);
            Restaurant bRestaurant = restaurants.get(b);

            return aRestaurant.getName().compareTo(bRestaurant.getName());
        });

        this.inspectionReports = inspectionReports;
        this.onRestaurantItemClick = onRestaurantItemClick;
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RestaurantItemBinding itemBinding = RestaurantItemBinding.inflate(layoutInflater, parent, false);
        return new RestaurantViewHolder(itemBinding, this.onRestaurantItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {

        String trackingNumber = this.trackingNumbers.get(position);

        Restaurant restaurant = this.restaurants.get(trackingNumber);
        InspectionReport report = this.inspectionReports.get(trackingNumber);

        holder.bind(restaurant, report);
    }

    @Override
    public int getItemCount() {
        return this.trackingNumbers != null ? this.trackingNumbers.size() : 0;
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantItemBinding binding;

        public RestaurantViewHolder(RestaurantItemBinding binding, IItemOnClickTrackingNumber onRestaurantItemClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener((View view) -> {
                onRestaurantItemClick.onItemClick(trackingNumbers.get(getAdapterPosition()));
            });
        }

        public void bind(Restaurant restaurant, InspectionReport inspectionReport) {
            this.binding.setRestaurant(restaurant);
            this.binding.setReport(inspectionReport);
            this.binding.executePendingBindings();
        }
    }
}
