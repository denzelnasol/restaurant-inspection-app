package com.group11.cmpt276_project.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantFilter;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//Singleton to handle the state of the main page. Currently it only manages the selectedTab
public class MainPageViewModel {

    private boolean isFilterApplied;

    private MutableLiveData<Boolean> expandFilter;

    private MediatorLiveData<RestaurantFilter> filter;
    private MediatorLiveData<Boolean> isLoadingDB;
    private MediatorLiveData<Integer> numberFiltersApplied;
    private MediatorLiveData<Pair<List<Restaurant>, List<InspectionReport>>> updates;

    public MutableLiveData<String> number;
    public MutableLiveData<Integer> isGrt;
    public MutableLiveData<Integer> hazardLevel;
    public MutableLiveData<Boolean> isFavorite;
    public MutableLiveData<Boolean> didUpdate;

    private int selectedTab;
    private String search;

    private LiveData<Map<String, List<InspectionReport>>> reports;
    private LiveData<Map<String, Violation>> violations;
    private LiveData<Map<String, Restaurant>> restaurants;
    private LiveData<List<String>> newInspections;

    private static class MainPageViewModelHolder {
        private static final MainPageViewModel INSTANCE = new MainPageViewModel();
    }

    public void init(LiveData<Map<String, List<InspectionReport>>> reports,
                     LiveData<Map<String, Violation>> violations, LiveData<Map<String,
            Restaurant>> restaurants, LiveData<List<String>> newInspections) {
        this.isLoadingDB = new MediatorLiveData<>();
        this.isLoadingDB.addSource(reports, (data) -> {
            this.checkIfLoadingIsDone();
        });
        this.isLoadingDB.addSource(violations, (data) -> {
            this.checkIfLoadingIsDone();
        });
        isLoadingDB.addSource(restaurants, (data) -> {
            this.checkIfLoadingIsDone();
        });

        this.didUpdate = new MutableLiveData<>(false);

        this.updates = new MediatorLiveData<>();

        this.updates.addSource(this.didUpdate, (data) -> {
            this.generateUpdateList();
        });
        this.updates.addSource(newInspections, (data) -> {
            this.generateUpdateList();
        });
        this.updates.addSource(this.isLoadingDB, (data) -> {
            this.generateUpdateList();
        });

        this.reports = reports;
        this.violations = violations;
        this.restaurants = restaurants;
        this.newInspections = newInspections;
    }

    public LiveData<RestaurantFilter> getFilter() {
        return this.filter;
    }

    private MainPageViewModel() {
        this.search = "";
        this.selectedTab = 0;

        this.expandFilter = new MutableLiveData<>(false);

        this.number = new MutableLiveData<>();
        this.isFavorite = new MutableLiveData<>(false);
        this.hazardLevel = new MutableLiveData<>();
        this.isGrt = new MutableLiveData<>();

        this.filter = new MediatorLiveData<>();

        this.filter.addSource(this.number, (data) -> {
            this.mergeQuery();
        });
        this.filter.addSource(this.isGrt, (data) -> {
            this.mergeQuery();
        });
        this.filter.addSource(this.isFavorite, (data) -> {
            this.mergeQuery();
        });
        this.filter.addSource(this.hazardLevel, (data) -> {
            this.mergeQuery();
        });


        this.numberFiltersApplied = new MediatorLiveData<>();

        this.numberFiltersApplied.addSource(this.number, (data) -> {
            this.updateNumberApplied();
        });
        this.numberFiltersApplied.addSource(this.isGrt, (data) -> {
            this.updateNumberApplied();
        });
        this.numberFiltersApplied.addSource(this.isFavorite, (data) -> {
            this.updateNumberApplied();
        });
        this.numberFiltersApplied.addSource(this.hazardLevel, (data) -> {
            this.updateNumberApplied();
        });

    }

    public static MainPageViewModel getInstance() {
        return MainPageViewModelHolder.INSTANCE;
    }

    public void setSelectedTab(int tab) {
        this.selectedTab = tab;
    }

    public int getSelectedTab() {
        return this.selectedTab;
    }

    public void mergeQuery() {
        Integer numberCritical = 0;

        String number = this.number.getValue();
        Integer isGrt = this.isGrt.getValue();
        Integer selectedHazardLevel = this.hazardLevel.getValue();
        Boolean isFavorite = this.isFavorite.getValue();

        if (!isNumberCriticalValid(number, isGrt) && !isHazardLevelValid(selectedHazardLevel) && !isFavorite) {
            this.filter.setValue(null);
            return;
        }


        if (number != null && !number.isEmpty() && isGrt != null) {
            int value = Integer.valueOf(number);
            numberCritical = isGrt == R.id.gte ? value : -value;
        }

        String hazardLevel = null;

        if (selectedHazardLevel != null) {
            switch (selectedHazardLevel) {
                case R.id.Low:
                    hazardLevel = Constants.LOW;
                    break;
                case R.id.Moderate:
                    hazardLevel = Constants.MODERATE;
                    break;
                case R.id.High:
                    hazardLevel = Constants.HIGH;
                    break;
                default:
                    hazardLevel = "";
            }
        }

        this.filter.setValue(new RestaurantFilter(hazardLevel, numberCritical, isFavorite));
    }

    private boolean isNumberCriticalValid(String number, Integer isGrt) {

        boolean grtValid = isGrt != null && (isGrt == R.id.gte || isGrt == R.id.lte);
        return (number != null && !number.isEmpty()) && grtValid;
    }

    private boolean isHazardLevelValid(Integer selectedHazardLevel) {
        return selectedHazardLevel != null && (selectedHazardLevel == R.id.Low || selectedHazardLevel == R.id.High || selectedHazardLevel == R.id.Moderate);
    }

    private void updateNumberApplied() {
        int numberApplied = 0;

        String number = this.number.getValue();
        Integer isGrt = this.isGrt.getValue();
        Integer selectedHazardLevel = this.hazardLevel.getValue();
        Boolean isFavorite = this.isFavorite.getValue();

        if (number != null && !number.isEmpty() && isGrt != null) {
            numberApplied++;
        }
        if (isFavorite != null & isFavorite) {
            numberApplied++;
        }

        if (selectedHazardLevel != null && selectedHazardLevel != 0) {
            numberApplied++;
        }

        this.numberFiltersApplied.setValue(numberApplied);
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public void clearFilter() {
        this.number.setValue(null);
        this.isGrt.setValue(null);
        this.hazardLevel.setValue(null);
        this.isFavorite.setValue(false);
        this.isFilterApplied = false;
    }

    public void clearHazardLevel() {
        this.hazardLevel.setValue(null);
    }

    public void clearNumberCritical() {
        this.number.setValue(null);
        this.isGrt.setValue(null);
    }

    public LiveData<Boolean> getExpandFilter() {
        return this.expandFilter;
    }

    public void closeFilter() {
        this.expandFilter.setValue(false);
    }

    public void toggleFilter() {
        this.expandFilter.setValue(!this.expandFilter.getValue());
    }

    public LiveData<Integer> getNumberFiltersApplied() {
        return numberFiltersApplied;
    }

    public boolean isFilterApplied() {
        return isFilterApplied;
    }

    public void setFilterApplied(boolean filterApplied) {
        isFilterApplied = filterApplied;
    }

    private void checkIfLoadingIsDone() {
        Map<String, List<InspectionReport>> reports = this.reports.getValue();
        Map<String, Violation> violations = this.violations.getValue();
        Map<String, Restaurant> restaurants = this.restaurants.getValue();

        if (reports != null && violations != null && restaurants != null) {
            this.isLoadingDB.setValue(false);
            return;
        }

        this.isLoadingDB.setValue(true);
    }

    public LiveData<Boolean> getIsLoadingDB() {
        return isLoadingDB;
    }

    public LiveData<Pair<List<Restaurant>, List<InspectionReport>>> getUpdates() {
        return updates;
    }

    public void setDidUpdate(boolean didUpdate) {
        this.didUpdate.setValue(didUpdate);
    }

    private void generateUpdateList() {

        List<String> newInspections = this.newInspections.getValue();

        boolean didUpdate = this.didUpdate.getValue() != null ? this.didUpdate.getValue() : false;
        boolean isLoadingDB = this.isLoadingDB.getValue() != null ? this.isLoadingDB.getValue() : true;

        if (!isLoadingDB && newInspections != null && !newInspections.isEmpty() && didUpdate) {

            Map<String, Restaurant> restaurants = this.restaurants.getValue();
            Map<String, List<InspectionReport>> reports = this.reports.getValue();

            List<Restaurant> updatedRestaurants = new ArrayList<>();
            List<InspectionReport> mostRecentReports = new ArrayList<>();

            for (String trackingNumber : newInspections) {
                if (reports.get(trackingNumber) == null || reports.get(trackingNumber).isEmpty()) {
                    continue;
                }

                Restaurant restaurant = restaurants.get(trackingNumber);

                if(restaurant != null) {
                    InspectionReport mostRecent = reports.get(trackingNumber).get(0);

                    updatedRestaurants.add(restaurant);
                    mostRecentReports.add(mostRecent);
                }
            }

            Collections.sort(updatedRestaurants, (Restaurant a, Restaurant b) -> a.getName().compareTo(b.getName()));
            Collections.sort(mostRecentReports, (InspectionReport a, InspectionReport b) -> {
                Restaurant aRestaurant = restaurants.get(a.getTrackingNumber());
                Restaurant bRestaurant = restaurants.get(b.getTrackingNumber());

                return aRestaurant.getName().compareTo(bRestaurant.getName());
            });
            this.updates.setValue(new Pair<>(updatedRestaurants, mostRecentReports));
        }
    }
}
