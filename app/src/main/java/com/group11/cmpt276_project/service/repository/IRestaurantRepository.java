package com.group11.cmpt276_project.service.repository;

import androidx.lifecycle.LiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantUpdate;

import java.util.List;

//An interface describing what functions a RestaurantRepository should have.
public interface IRestaurantRepository {

    LiveData<List<Restaurant>>  getRestaurants() throws RepositoryReadError;
    LiveData<List<Restaurant>> getRestaurantsBySearch(String name, boolean isFavorite, int numberCritical, String hazardLevel) throws  RepositoryReadError;
    void saveRestaurants(List<RestaurantUpdate> restaurants) throws RepositoryWriteError;
    void saveRestaurant(Restaurant restaurant) throws RepositoryWriteError;

}
