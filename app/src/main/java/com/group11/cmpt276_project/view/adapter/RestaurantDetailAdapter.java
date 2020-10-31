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
import com.group11.cmpt276_project.service.model.InspectionReport;

import java.util.List;

//Adapter for setting List of inspections in Restaurant detail activity
public class RestaurantDetailAdapter extends RecyclerView.Adapter<RestaurantDetailAdapter.ViewHolder> {
    List<InspectionReport> inspectionList;
    int [] hazardLevelIconsPack;
    Context context;

    public RestaurantDetailAdapter(Context context, List<InspectionReport> inspectionReportList, int[] img){
        inspectionList = inspectionReportList;
        this.context = context;
        hazardLevelIconsPack = img;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View setView = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new ViewHolder(setView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int setter = inspectionList[position].getNumberCritical();
        holder.num_critical.setText(String.valueOf(setter));

        setter = inspectionList[position].getNumberNonCritical();
        holder.num_non_critical.setText(String.valueOf(setter));

        setter = inspectionList[position].getInspectionDate();
        //To DO - format time
        holder.time.setText(String.valueOf(setter));

        //Set img according to hazard icon
        holder.recycler_row_img.setImageResource(hazardLevelIconsPack[0]);
    }

    @Override
    public int getItemCount() {
        return inspectionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView num_critical, num_non_critical, time;
        ImageView recycler_row_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            num_critical = itemView.findViewById(R.id.res_detail_critical);
            num_non_critical = itemView.findViewById(R.id.res_detail_non_cric);
            time = itemView.findViewById(R.id.res_detail_time);
            recycler_row_img = itemView.findViewById(R.id.recycler_row_icon);
        }
    }
}
