package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.dto.RestaurantDto;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantUpdate;
import com.group11.cmpt276_project.service.repository.IRestaurantRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This class is a singleton that contains the restaurants data provided. The class stores the data in
an ArrayList to allow quick updates should it be needed. It also returns a LiveData version of the list
so anything observing will change accordingly
 */
public class RestaurantsViewModel {

    private LiveData<List<Restaurant>> mData;
    private MediatorLiveData<Map<String, Restaurant>> mRestaurants;

    private IRestaurantRepository restaurantRepository;

    private RestaurantsViewModel() {

    }

    private static class RestaurantsViewModelHolder {
        private static final RestaurantsViewModel INSTANCE = new RestaurantsViewModel();
    }

    public static RestaurantsViewModel getInstance() {
        return RestaurantsViewModelHolder.INSTANCE;
    }

    public void init(IRestaurantRepository jsonRestaurantRepository) {
        if (this.restaurantRepository == null) {
            this.restaurantRepository = jsonRestaurantRepository;

            try {
                this.mData = this.restaurantRepository.getRestaurants();
            } catch (RepositoryReadError repositoryReadError) {
                this.mData = new MutableLiveData<>();
            }

            this.mRestaurants = new MediatorLiveData<>();
            this.mRestaurants.addSource(this.mData, (data) -> {

                if(data == null) return;

                Map<String, Restaurant> restaurants = new HashMap<>();

                for(Restaurant restaurant : data) {
                    restaurants.put(restaurant.getTrackingNumber(), restaurant);
                }

                this.mRestaurants.setValue(restaurants);
            });
        }
    }

    public LiveData<Map<String, Restaurant>> getRestaurants() {
        return this.mRestaurants;
    }

    public void save(List<RestaurantDto> newRestaurants) {
        try {

            List<RestaurantUpdate> toSave = new ArrayList<>();

            for (RestaurantDto restaurantDto: newRestaurants) {
                String trackingNumber = restaurantDto.getTrackingNumber();
                String newFacilityType = restaurantDto.getFacilityType();
                double newLatitude = restaurantDto.getLatitude();
                double newLongitude = restaurantDto.getLongitude();
                String newPhysicalAddress = restaurantDto.getPhysicalAddress();
                String newPhysicalCity = restaurantDto.getPhysicalCity();
                String newName = restaurantDto.getName();


                RestaurantUpdate restaurant = new RestaurantUpdate.RestaurantUpdateBuilder()
                            .withFacilityType(newFacilityType)
                            .withLatitude(newLatitude)
                            .withLongitude(newLongitude)
                            .withPhysicalAddress(newPhysicalAddress)
                            .withPhysicalCity(newPhysicalCity)
                            .withName(newName)
                            .withTrackingNumber(trackingNumber)
                            .build();

                toSave.add(restaurant);
            }

            this.restaurantRepository.saveRestaurants(toSave);
        } catch (RepositoryWriteError repositoryWriteError) {
            repositoryWriteError.printStackTrace();
        }
    }
}
