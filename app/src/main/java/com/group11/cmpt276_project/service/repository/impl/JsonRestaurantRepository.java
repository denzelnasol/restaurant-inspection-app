package com.group11.cmpt276_project.service.repository.impl;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.service.repository.IRestaurantRepository;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/*This class serves to load the restaurants json file
 * The get function will load the json file using getJsonFromAssets util function and maps it to a
 * list using Jackson
 * */
public class JsonRestaurantRepository implements IRestaurantRepository {

    private Context context;
    private ObjectMapper objectMapper;

    public JsonRestaurantRepository(Context context) {

        this.context = context;
        this.objectMapper = new ObjectMapper();
      //  this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public Map<String, Restaurant> getRestaurants() throws RepositoryReadError {
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

        TypeReference<Map<String, Restaurant>> mapTypeReference = new TypeReference<Map<String, Restaurant>>() {
        };

        try {
            return objectMapper.readValue(jsonString, mapTypeReference);
        } catch (JsonProcessingException e) {
            throw new RepositoryReadError(e.getMessage());
        }
    }

    @Override
    public Map<String, Restaurant> saveRestaurants(Map<String, Restaurant> restaurants) throws RepositoryWriteError {
        try {
            String jsonString = this.objectMapper.writeValueAsString(restaurants);
            Utils.writeJSONToStorage(this.context, Constants.RESTAURANT_FILE, jsonString);
            return restaurants;
        } catch (IOException e) {
            throw new RepositoryWriteError(e.getMessage());
        }
    }
}
