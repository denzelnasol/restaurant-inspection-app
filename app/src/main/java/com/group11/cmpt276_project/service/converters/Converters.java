package com.group11.cmpt276_project.service.converters;

import androidx.room.TypeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Converters {

    @TypeConverter
    public static List<String> fromString(String value) {

        TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {
        };

        try {
            return new ObjectMapper().readValue(value, typeReference);
        } catch (JsonProcessingException e) {
           return new ArrayList<>();
        }
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        try {
            return new ObjectMapper().writeValueAsString(list);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
