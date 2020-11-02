package com.group11.cmpt276_project.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.InspectionItemsBinding;
import com.group11.cmpt276_project.databinding.InspectionItemsBindingImpl;
import com.group11.cmpt276_project.databinding.RestaurantItemBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.view.adapter.interfaces.IItemOnClick;

import java.util.List;

import static com.group11.cmpt276_project.BR.restaurant;

//Adapter for setting List of inspections in Restaurant detail activity
public class RestaurantDetailAdapter extends RecyclerView.Adapter<RestaurantDetailAdapter.ViewHolder> {
    private List<InspectionReport> inspectionList;
   private IItemOnClick iItemOnClick;



    public RestaurantDetailAdapter(List<InspectionReport> inspectionReports, IItemOnClick itemOnClick){
        inspectionList = inspectionReports;
        this.iItemOnClick = itemOnClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        InspectionItemsBinding itemBinding = InspectionItemsBinding.inflate(layoutInflater, parent, false);
        return new RestaurantDetailAdapter.ViewHolder(itemBinding, iItemOnClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InspectionReport inspectionReport = this.inspectionList.get(position);
        holder.bind(inspectionReport);
    }

    @Override
    public int getItemCount() {
        return inspectionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView num_critical, num_non_critical, time;
        ImageView recycler_row_img;
        private InspectionItemsBinding binding;
        public ViewHolder(InspectionItemsBinding binding, IItemOnClick inspectionOnClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener((View view) -> {
                inspectionOnClick.onItemClick(getAdapterPosition());
            });
        }
            public void bind( InspectionReport inspectionReport) {
                this.binding.setReport(inspectionReport);
                this.binding.executePendingBindings();
            }
        }

}
