package com.group11.cmpt276_project.viewmodel.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.viewmodel.MapFragmentViewModel;

import java.util.List;
import java.util.Map;


public class MapFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;
    private final LiveData<Map<String, Restaurant>> restaurants;
    private final LiveData<Map<String, List<InspectionReport>>> inspectionReports;

    public MapFragmentViewModelFactory(Context context, LiveData<Map<String, Restaurant>> restaurants, LiveData<Map<String, List<InspectionReport>>> inspectionReports) {
        this.context = context;
        this.restaurants = restaurants;
        this.inspectionReports = inspectionReports;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MapFragmentViewModel.class)) {
            return (T) new MapFragmentViewModel(this.context, this.restaurants, this.inspectionReports);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
