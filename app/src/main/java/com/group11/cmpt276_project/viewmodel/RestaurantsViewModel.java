package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.repository.RestaurantRepository;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

/*
This class is a singleton that contains the restaurants data provided. The class stores the data in
an ArrayList to allow quick updates should it be needed. It also returns a LiveData version of the list
so anything observing will change accordingly
 */
public class RestaurantsViewModel {

    private Map<String,Restaurant> restaurants;
    private MutableLiveData<Map<String,Restaurant>> mRestaurants;

    private RestaurantRepository restaurantRepository;

    private RestaurantsViewModel() {

    }

    private static class RestaurantsViewModelHolder {
        private static RestaurantsViewModel INSTANCE = new RestaurantsViewModel();
    }

    public static RestaurantsViewModel getInstance() {
        return RestaurantsViewModelHolder.INSTANCE;
    }

    public void init(RestaurantRepository restaurantRepository) {
        if (this.restaurantRepository == null) {
            this.restaurantRepository = restaurantRepository;

            try {
                this.restaurants = this.restaurantRepository.getFromAssets();
            } catch (IOException e) {
                this.restaurants = new TreeMap<>();
            }

            this.mRestaurants = new MutableLiveData<>(this.restaurants);
        }
    }

    public Restaurant getByTrackingNumber(String trackingNumber) {
        return this.mRestaurants.getValue().get(trackingNumber);
    }

    public LiveData<Map<String,Restaurant>> get() {
        return this.mRestaurants;
    }
}
