package com.group11.cmpt276_project.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantListFragmentViewModel extends ViewModel {

    private MediatorLiveData<Pair<Map<String, Restaurant>, Map<String, InspectionReport>>> mData;

    private Map<String, Restaurant> restaurants;
    private Map<String, List<InspectionReport>> inspectionReports;

    public RestaurantListFragmentViewModel(LiveData<Map<String, Restaurant>> restaurants, LiveData<Map<String, List<InspectionReport>>> inspectionReports) {
        this.mData = new MediatorLiveData<>();
        this.mData.addSource(restaurants, (data) -> {
            this.restaurants = data;
            this.mergeSources();
        });
        this.mData.addSource(inspectionReports, (data) -> {
            this.inspectionReports = data;
            this.mergeSources();
        });
    }

    public LiveData<Pair<Map<String, Restaurant>, Map<String, InspectionReport>>> getData() {
        return this.mData;
    }

    private void mergeSources() {
        if(this.restaurants!= null && this.inspectionReports != null) {
            Map<String, InspectionReport> reports = new HashMap<>();

            for (String trackingNumber : restaurants.keySet()) {

                List<InspectionReport> inspections = this.inspectionReports.getOrDefault(trackingNumber, new ArrayList<>());

                if(!inspections.isEmpty()) {
                    reports.put(trackingNumber, inspections.get(0));
                }
            }

            this.mData.setValue(new Pair<>(this.restaurants, reports));
        }
    }
}
