package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.repository.RestaurantRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsViewModel {

    private List<Restaurant> restaurantList;
    private MutableLiveData<List<Restaurant>> mRestaurantList;

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
                this.restaurantList = this.restaurantRepository.get();
            } catch (IOException e) {
                this.restaurantList = new ArrayList<>();
            }

            this.mRestaurantList = new MutableLiveData<>(this.restaurantList);
        }
    }

    public Restaurant getByIndex(int index) {
        return this.mRestaurantList.getValue().get(index);
    }

    public LiveData<List<Restaurant>> get() {
        return this.mRestaurantList;
    }
}
