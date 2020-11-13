package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.repository.IRestaurantRepository;
import com.group11.cmpt276_project.service.repository.impl.JsonRestaurantRepository;

import java.util.HashMap;
import java.util.Map;

/*
This class is a singleton that contains the restaurants data provided. The class stores the data in
an ArrayList to allow quick updates should it be needed. It also returns a LiveData version of the list
so anything observing will change accordingly
 */
public class RestaurantsViewModel {

    private Map<String, Restaurant> restaurants;
    private MutableLiveData<Map<String, Restaurant>> mRestaurants;

    private IRestaurantRepository restaurantRepository;

    private RestaurantsViewModel() {

    }

    private static class RestaurantsViewModelHolder {
        private static RestaurantsViewModel INSTANCE = new RestaurantsViewModel();
    }

    public static RestaurantsViewModel getInstance() {
        return RestaurantsViewModelHolder.INSTANCE;
    }

    public void init(IRestaurantRepository jsonRestaurantRepository) {
        if (this.restaurantRepository == null) {
            this.restaurantRepository = jsonRestaurantRepository;

            try {
                this.restaurants = this.restaurantRepository.getRestaurants();
            } catch (RepositoryReadError repositoryReadError) {
                this.restaurants = new HashMap<>();
            }

            this.mRestaurants = new MutableLiveData<>(this.restaurants);
        }
    }

    public void add(Map<String, Restaurant> newRestaurants) {
        this.restaurants.putAll(newRestaurants);
        this.mRestaurants.postValue(this.restaurants);
    }

    public Restaurant getByTrackingNumber(String trackingNumber) {
        return this.mRestaurants.getValue().get(trackingNumber);
    }

    public LiveData<Map<String, Restaurant>> get() {
        return this.mRestaurants;
    }

    public void save() {
        try {
            this.restaurantRepository.saveRestaurants(this.restaurants);
        } catch (RepositoryWriteError repositoryWriteError) {
            repositoryWriteError.printStackTrace();
        }
    }
}
