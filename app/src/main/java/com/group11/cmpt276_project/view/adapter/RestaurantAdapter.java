package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.RestaurantItemBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is an adapter to hook the RecyclerView with DataBinding for the restaurant list.
 **/
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Map<String, Restaurant> restaurants;
    private List<String> trackingNumbers;
    private Map<String, InspectionReport> inspectionReports;
    private final IRestaurantItemOnClick onRestaurantItemClick;
    private final IFavouriteOnClick onFavouriteClick;

    public RestaurantAdapter(IRestaurantItemOnClick onRestaurantItemClick, IFavouriteOnClick onFavouriteClick) {
        this.onRestaurantItemClick = onRestaurantItemClick;
        this.onFavouriteClick = onFavouriteClick;

        this.restaurants = new HashMap<>();
        this.inspectionReports = new HashMap<>();
        this.trackingNumbers = new ArrayList<>(restaurants.keySet());
    }

    public void postUpdates(Map<String, Restaurant> restaurants, Map<String, InspectionReport> inspectionReports) {
        this.restaurants = restaurants;
        this.inspectionReports = inspectionReports;
        this.trackingNumbers = new ArrayList<>(restaurants.keySet());

        Collections.sort(this.trackingNumbers, (String a, String b) -> {
            Restaurant aRestaurant = restaurants.get(a);
            Restaurant bRestaurant = restaurants.get(b);

            return aRestaurant.getName().toLowerCase().compareTo(bRestaurant.getName().toLowerCase());
        });
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RestaurantItemBinding itemBinding = RestaurantItemBinding.inflate(layoutInflater, parent, false);
        return new RestaurantViewHolder(itemBinding, this.onRestaurantItemClick, this.onFavouriteClick);
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

        public RestaurantViewHolder(RestaurantItemBinding binding, IRestaurantItemOnClick onRestaurantItemClick, IFavouriteOnClick onFavouriteClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener((view) -> {
                onRestaurantItemClick.onItemClick(trackingNumbers.get(getAdapterPosition()));
            });
            this.binding.favouritedImage.setOnClickListener((view) -> {
                onFavouriteClick.onClick(trackingNumbers.get(getAdapterPosition()));
            });
        }

        public void bind(Restaurant restaurant, InspectionReport inspectionReport) {
            this.binding.setRestaurant(restaurant);
            this.binding.setReport(inspectionReport);
            this.binding.executePendingBindings();
        }
    }

    public interface IRestaurantItemOnClick {
        void onItemClick(String trackingNumber);
    }

    public interface IFavouriteOnClick {
        void onClick(String trackingNumber);
    }

}
