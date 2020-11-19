package com.group11.cmpt276_project.service.repository;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Restaurant;

import java.util.Map;

//An interface describing what functions a RestaurantRepository should have.
public interface IRestaurantRepository {

    Map<String,Restaurant>  getRestaurants() throws RepositoryReadError;
    Map<String,Restaurant> saveRestaurants(Map<String,Restaurant> restaurants) throws RepositoryWriteError;
}
