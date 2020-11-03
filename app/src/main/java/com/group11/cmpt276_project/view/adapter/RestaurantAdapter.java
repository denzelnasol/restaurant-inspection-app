package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.RestaurantItemBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.view.adapter.interfaces.IItemOnClick;

import java.util.List;
import java.util.Random;
/**
 This class is an adapter to hook the RecyclerView with DataBinding for the restaurant list.
 **/
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>{

    private List<Restaurant> restaurants;
    private  List<InspectionReport> inspectionReports;
    private IItemOnClick onRestaurantItemClick;

    public RestaurantAdapter(List<Restaurant> restaurants, List<InspectionReport> inspectionReports, IItemOnClick onRestaurantItemClick) {
        this.restaurants = restaurants;
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
        Restaurant restaurant = this.restaurants.get(position);
        InspectionReport report = this.inspectionReports.get(position);

        holder.bind(restaurant, report);
    }

    @Override
    public int getItemCount() {
        return this.restaurants != null ? this.restaurants.size() : 0;
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private RestaurantItemBinding binding;

        public RestaurantViewHolder(RestaurantItemBinding binding, IItemOnClick onRestaurantItemClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener((View view) -> {
                onRestaurantItemClick.onItemClick(getAdapterPosition());
            });
        }

        public void bind(Restaurant restaurant, InspectionReport inspectionReport) {
            this.binding.setRestaurant(restaurant);
            this.binding.setReport(inspectionReport);
            this.binding.executePendingBindings();
        }
    }
}
