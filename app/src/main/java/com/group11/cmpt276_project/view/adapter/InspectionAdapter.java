package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.InspectionItemsBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;

import java.util.List;

//Adapter for setting List of inspections in Restaurant detail activity
public class InspectionAdapter extends RecyclerView.Adapter<InspectionAdapter.ViewHolder> {
    private List<InspectionReport> inspectionList;
    private IInspectionItemOnClick iItemOnClickTrackingNumber;


    public InspectionAdapter(List<InspectionReport> inspectionReports, IInspectionItemOnClick itemOnClick) {
        inspectionList = inspectionReports;
        this.iItemOnClickTrackingNumber = itemOnClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        InspectionItemsBinding itemBinding = InspectionItemsBinding.inflate(layoutInflater, parent, false);
        return new InspectionAdapter.ViewHolder(itemBinding, iItemOnClickTrackingNumber);
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private InspectionItemsBinding binding;

        public ViewHolder(InspectionItemsBinding binding, IInspectionItemOnClick inspectionOnClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener((View view) -> {
                inspectionOnClick.onItemClick( getAdapterPosition());
            });
        }

        public void bind(InspectionReport inspectionReport) {
            this.binding.setReport(inspectionReport);
            this.binding.executePendingBindings();
        }
    }

    public interface IInspectionItemOnClick {
        void onItemClick(int index);
    }
}
