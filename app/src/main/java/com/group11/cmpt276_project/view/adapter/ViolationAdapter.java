package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.ViolationItemBinding;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.view.adapter.interfaces.IItemOnClickIndex;

import java.util.List;
/**
This class is an adapter to hook the RecyclerView with DataBinding for the violation list.
 **/
public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder>{

    private final List<Violation> violations;
    private final boolean[] isVisibleData;
    private final IItemOnClickIndex onViolationItemClick;

    public ViolationAdapter(List<Violation> violations, boolean[] isVisibleData, IItemOnClickIndex onViolationItemClick) {
        this.violations = violations;
        this.isVisibleData = isVisibleData;
        this.onViolationItemClick = onViolationItemClick;
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViolationItemBinding itemBinding = ViolationItemBinding.inflate(layoutInflater, parent, false);
        return new ViolationViewHolder(itemBinding, this.onViolationItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        Violation violation = violations.get(position);
        boolean isVisible = isVisibleData[position];
        holder.bind(violation, isVisible);
    }

    @Override
    public int getItemCount() {
        return this.violations != null ? this.violations.size() : 0;
    }

    class ViolationViewHolder extends RecyclerView.ViewHolder {

        private final ViolationItemBinding binding;

        public ViolationViewHolder(ViolationItemBinding binding, IItemOnClickIndex onViolationItemClick) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener((View view) -> {
                onViolationItemClick.onItemClick(getAdapterPosition());
            });
        }

        public void bind(Violation violation, boolean isVisible) {
            this.binding.setViolation(violation);
            this.binding.executePendingBindings();
            this.binding.setIsVisible(isVisible);
        }
    }
}
