package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.model.RestaurantFilter;
import com.group11.cmpt276_project.utils.Constants;

//Singleton to handle the state of the main page. Currently it only manages the selectedTab
public class MainPageViewModel {

    private MutableLiveData<Boolean> expandFilter;

    public MutableLiveData<String> number;
    public MutableLiveData<Integer> isGrt;
    public MutableLiveData<Integer> hazardLevel;
    public MutableLiveData<Boolean> isFavorite;

    private MediatorLiveData<RestaurantFilter> filter;

    private int selectedTab;
    private String search;

    private static class MainPageViewModelHolder {
        private static final MainPageViewModel INSTANCE = new MainPageViewModel();
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

        if (number != null && !number.isEmpty() && isGrt != null) {
            int value = Integer.valueOf(number);
            numberCritical= isGrt == R.id.gte ? value : -value;
        }

        Integer selectedHazardLevel = this.hazardLevel.getValue();
        Boolean isFavorite = this.isFavorite.getValue();

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
    }

    public void clearAll() {
        this.number.setValue(null);
        this.isGrt.setValue(null);
        this.hazardLevel.setValue(null);
        this.isFavorite.setValue(false);
        this.search = "";
    }

    public LiveData<Boolean> getExpandFilter() {
        return this.expandFilter;
    }

    public void setExpandFilter(boolean expandFilter) {
        this.expandFilter.setValue(expandFilter);
    }
}
