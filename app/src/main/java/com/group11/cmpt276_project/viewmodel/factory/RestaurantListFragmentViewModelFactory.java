package com.group11.cmpt276_project.viewmodel.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.viewmodel.RestaurantListFragmentViewModel;

import java.util.List;
import java.util.Map;

public class RestaurantListFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final LiveData<Map<String, Restaurant>> restaurants;
    private final LiveData<Map<String, List<InspectionReport>>> inspectionReports;

    public RestaurantListFragmentViewModelFactory(LiveData<Map<String, Restaurant>> restaurants, LiveData<Map<String, List<InspectionReport>>> inspectionReports) {
        this.restaurants = restaurants;
        this.inspectionReports = inspectionReports;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RestaurantListFragmentViewModel.class)) {
            return (T) new RestaurantListFragmentViewModel(this.restaurants, this.inspectionReports);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
