package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.RestaurantItemBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;

import java.util.List;
import java.util.Random;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private List<Restaurant> restaurants;

    public RestaurantAdapter(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }


    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RestaurantItemBinding itemBinding = RestaurantItemBinding.inflate(layoutInflater, parent, false);
        return new RestaurantViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Random random = new Random();
        Restaurant restaurant = this.restaurants.get(position);
        //This is temporary
        holder.bind(restaurant, new InspectionReport(restaurant.getTrackingNumber(), 20181106, "Follow-Up", random.nextInt(5), random.nextInt(5), "Low", new int[0]));
    }

    @Override
    public int getItemCount() {
        return this.restaurants != null ? this.restaurants.size() : 0;
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private RestaurantItemBinding binding;

        public RestaurantViewHolder(RestaurantItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Restaurant restaurant, InspectionReport inspectionReport) {
            binding.setRestaurant(restaurant);
            binding.setReport(inspectionReport);
            binding.executePendingBindings();
        }
    }
}
