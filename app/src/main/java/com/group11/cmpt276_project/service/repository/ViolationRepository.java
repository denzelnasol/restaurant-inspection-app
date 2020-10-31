package com.group11.cmpt276_project.service.repository;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ViolationRepository {
    private Context context;
    private ObjectMapper objectMapper;

    public ViolationRepository(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Map<String, Violation> get() throws IOException {
        String jsonString = Utils.getJsonFromAssets(this.context, Constants.VIOLATION_FILE);
        TypeReference<Map<String, Violation>> mapTypeReference = new TypeReference<Map<String, Violation>>() {};
        return objectMapper.readValue(jsonString, mapTypeReference);
    }
}
