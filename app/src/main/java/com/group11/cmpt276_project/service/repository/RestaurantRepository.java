package com.group11.cmpt276_project.service.repository;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/*This class serves to load the restaurants json file
* The get function will load the json file using getJsonFromAssets util function and maps it to a
* list using Jackson
* */
public class RestaurantRepository {

    private Context context;
    private ObjectMapper objectMapper;

    public RestaurantRepository(Context context) {

        this.context = context;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Map<String,Restaurant> getFromAssets() throws IOException {

        String jsonString = Utils.getJsonFromAssets(this.context, Constants.RESTAURANT_FILE);
        TypeReference<TreeMap<String,Restaurant>> mapTypeReference = new TypeReference<TreeMap<String,Restaurant>>() {};
        return objectMapper.readValue(jsonString, mapTypeReference);
    }
}
