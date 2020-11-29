package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.UpdateItemBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;

import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder>{

    private List<Restaurant> restaurants;
    private List<InspectionReport> reports;

    public UpdateAdapter(List<Restaurant> restaurants, List<InspectionReport> reports) {
        this.restaurants = restaurants;
        this.reports = reports;
    }

    @NonNull
    @Override
    public UpdateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        UpdateItemBinding itemBinding = UpdateItemBinding.inflate(layoutInflater, parent, false);
        return new UpdateAdapter.UpdateViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateViewHolder holder, int position) {
        holder.bind(this.reports.get(position), this.restaurants.get(position));
    }

    @Override
    public int getItemCount() {
        return this.restaurants != null ? this.restaurants.size() : 0;
    }

    class UpdateViewHolder extends RecyclerView.ViewHolder {

        private UpdateItemBinding binding;

        public UpdateViewHolder(UpdateItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(InspectionReport report, Restaurant restaurant) {
            this.binding.setReport(report);
            this.binding.setRestaurant(restaurant);
            this.binding.executePendingBindings();
        }
    }
}
