package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.UpdateItemBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;

import java.util.List;

/**
 * This class is an adapter to hook the RecyclerView with DataBinding for the update list.
 */
public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.FavoriteViewHolder>{

    private List<Restaurant> restaurants;
    private List<InspectionReport> inspectionReports;

    public UpdateAdapter(List<Restaurant> restaurants, List<InspectionReport> inspectionReports) {
        this.restaurants = restaurants;
        this.inspectionReports = inspectionReports;
    }

    @NonNull
    @Override
    public UpdateAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        UpdateItemBinding itemBinding = UpdateItemBinding.inflate(layoutInflater, parent, false);
        return new UpdateAdapter.FavoriteViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateAdapter.FavoriteViewHolder holder, int position) {
        Restaurant restaurant = this.restaurants.get(position);
        InspectionReport report = this.inspectionReports.get(position);

        holder.bind(restaurant, report);
    }

    @Override
    public int getItemCount() {
        return this.restaurants != null ? this.restaurants.size() : 0;
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        private final UpdateItemBinding binding;

        public FavoriteViewHolder(UpdateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Restaurant restaurant, InspectionReport inspectionReport) {
            this.binding.setRestaurant(restaurant);
            this.binding.setReport(inspectionReport);
            this.binding.executePendingBindings();
        }
    }
}
