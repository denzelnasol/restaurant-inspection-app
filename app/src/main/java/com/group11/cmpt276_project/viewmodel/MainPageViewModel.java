package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantFilter;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.utils.Constants;

import java.util.List;
import java.util.Map;

//Singleton to handle the state of the main page. Currently it only manages the selectedTab
public class MainPageViewModel {

    private boolean isFilterApplied;

    private MutableLiveData<Boolean> expandFilter;

    private MediatorLiveData<Integer> numberFiltersApplied;

    public MutableLiveData<String> number;
    public MutableLiveData<Integer> isGrt;
    public MutableLiveData<Integer> hazardLevel;
    public MutableLiveData<Boolean> isFavorite;

    private MediatorLiveData<RestaurantFilter> filter;
    private MediatorLiveData<Boolean> isLoadingDB;

    private int selectedTab;
    private String search;

    private LiveData<Map<String, List<InspectionReport>>> reports;
    private LiveData<Map<String, Violation>> violations;
    private LiveData<Map<String, Restaurant>> restaurants;

    private static class MainPageViewModelHolder {
        private static final MainPageViewModel INSTANCE = new MainPageViewModel();
    }

    public void init(LiveData<Map<String, List<InspectionReport>>> reports, LiveData<Map<String, Violation>> violations, LiveData<Map<String, Restaurant>> restaurants) {
        this.isLoadingDB = new MediatorLiveData<>();
        this.isLoadingDB.addSource(reports, (data) -> {
            this.checkIfLoadingIsDone();
        });
        this.isLoadingDB.addSource(violations, (data) -> {
            this.checkIfLoadingIsDone();
        });
        this.isLoadingDB.addSource(restaurants, (data) -> {
            this.checkIfLoadingIsDone();
        });

        this.reports = reports;
        this.violations = violations;
        this.restaurants = restaurants;
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
            this.isLoadingDB.setValue(true);
            return;
        }

        this.isLoadingDB.setValue(false);
    }

    public LiveData<Boolean> getIsLoadingDB() {
        return isLoadingDB;
    }
}
