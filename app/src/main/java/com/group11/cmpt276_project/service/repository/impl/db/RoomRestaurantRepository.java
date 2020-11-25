package com.group11.cmpt276_project.service.repository.impl.db;

import androidx.lifecycle.LiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.dao.RestaurantDao;
import com.group11.cmpt276_project.service.db.RestaurantDatabase;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantUpdate;
import com.group11.cmpt276_project.service.repository.IRestaurantRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomRestaurantRepository implements IRestaurantRepository {

    private RestaurantDao restaurantDao;
    private LiveData<List<Restaurant>> restaurants;

    public RoomRestaurantRepository(RestaurantDao restaurantDao) {
        this.restaurantDao = restaurantDao;
        this.restaurants = this.restaurantDao.getAllRestaurants();
    }

    @Override
    public LiveData<List<Restaurant>> getRestaurants() throws RepositoryReadError {
        return this.restaurants;
    }

    @Override
    public LiveData<List<String>> getFavoriteRestaurants() throws RepositoryReadError {
        return null;
    }

    @Override
    public void saveRestaurants(List<RestaurantUpdate> restaurants) throws RepositoryWriteError {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            this.restaurantDao.insertOrUpdate(restaurants);
        });
    }

    @Override
    public void saveRestaurant(RestaurantUpdate restaurant) throws RepositoryWriteError {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            this.restaurantDao.insertOrUpdate(restaurant);
        });
    }
}

