package com.group11.cmpt276_project.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.group11.cmpt276_project.databinding.ViolationItemBinding;
import com.group11.cmpt276_project.service.model.Violation;

import java.util.List;

public class ViolationAdapter extends RecyclerView.Adapter<ViolationAdapter.ViolationViewHolder>{

    private final List<Violation> violations;

    public ViolationAdapter(List<Violation> violations) {
        this.violations = violations;
    }

    @NonNull
    @Override
    public ViolationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViolationItemBinding itemBinding = ViolationItemBinding.inflate(layoutInflater, parent, false);
        return new ViolationViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViolationViewHolder holder, int position) {
        Violation violation = violations.get(position);
        holder.bind(violation);
    }

    @Override
    public int getItemCount() {
        return this.violations != null ? this.violations.size() : 0;
    }

    class ViolationViewHolder extends RecyclerView.ViewHolder {

        private final ViolationItemBinding binding;

        public ViolationViewHolder(ViolationItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Violation violation) {
            this.binding.setViolation(violation);
            this.binding.executePendingBindings();
        }
    }
}
