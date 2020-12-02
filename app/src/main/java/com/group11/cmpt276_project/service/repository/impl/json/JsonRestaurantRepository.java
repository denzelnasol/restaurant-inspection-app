package com.group11.cmpt276_project.service.repository.impl.json;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.model.RestaurantUpdate;
import com.group11.cmpt276_project.service.repository.IRestaurantRepository;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*This class serves to load the restaurants json file
 * The get function will load the json file using getJsonFromAssets util function and maps it to a
 * list using Jackson
 * */
public class JsonRestaurantRepository implements IRestaurantRepository {

    private Context context;
    private ObjectMapper objectMapper;
    private MutableLiveData<List<Restaurant>> restaurants;

    public JsonRestaurantRepository(Context context) {

        this.context = context;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public LiveData<List<Restaurant>> getRestaurants() throws RepositoryReadError {

        if (restaurants != null) {
            return this.restaurants;
        }

        String jsonString;

        try {
            jsonString = Utils.readJSONFromStorage(this.context, Constants.RESTAURANT_FILE);
        } catch (IOException e) {
            try {
                jsonString = Utils.readJsonFromAssets(this.context, Constants.RESTAURANT_FILE);
            } catch (IOException ioException) {
                throw new RepositoryReadError(ioException.getMessage());
            }
        }

        TypeReference<List<Restaurant>> typeReference = new TypeReference<List<Restaurant>>() {
        };

        try {
            this.restaurants = new MutableLiveData<>(objectMapper.readValue(jsonString, typeReference));
            return this.restaurants;
        } catch (JsonProcessingException e) {
            throw new RepositoryReadError(e.getMessage());
        }
    }

    @Override
    public LiveData<List<Restaurant>> getFavouriteRestaurants() throws RepositoryReadError {
        return null;
    }

    @Override
    public LiveData<List<Restaurant>> getRestaurantsBySearch(String name, boolean isFavorite, int numberCritical, String hazardLevel) throws RepositoryReadError {
        return null;
    }

    @Override
    public void saveRestaurants(List<RestaurantUpdate> restaurants) throws RepositoryWriteError {
        try {
            String jsonString = this.objectMapper.writeValueAsString(restaurants);
            Utils.writeJSONToStorage(this.context, Constants.RESTAURANT_FILE, jsonString);

            List<Restaurant> updates = new ArrayList<>();

            for (RestaurantUpdate restaurantUpdate : restaurants) {

                String trackingNumber = restaurantUpdate.getTrackingNumber();
                String newFacilityType = restaurantUpdate.getFacilityType();
                double newLatitude = restaurantUpdate.getLatitude();
                double newLongitude = restaurantUpdate.getLongitude();
                String newPhysicalAddress = restaurantUpdate.getPhysicalAddress();
                String newPhysicalCity = restaurantUpdate.getPhysicalCity();
                String newName = restaurantUpdate.getName();

                Restaurant restaurant = new Restaurant.RestaurantBuilder()
                        .withFacilityType(newFacilityType)
                        .withLatitude(newLatitude)
                        .withLongitude(newLongitude)
                        .withIsFavorite(false)
                        .withPhysicalAddress(newPhysicalAddress)
                        .withPhysicalCity(newPhysicalCity)
                        .withName(newName)
                        .withTrackingNumber(trackingNumber)
                        .withIsFavorite(false)
                        .build();

                updates.add(restaurant);
            }

            this.restaurants.postValue(updates);
        } catch (IOException e) {
            throw new RepositoryWriteError(e.getMessage());
        }
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) throws RepositoryWriteError {
    }


}
