package com.group11.cmpt276_project.service.repository.impl;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.repository.IInspectionReportRepository;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* This class serves to load the inspection report json file
 * The get function will load the json file using getJsonFromAssets util function and implement
 * the string and the list as a map using Jackson
 * */

public class JsonInspectionReportRepository implements IInspectionReportRepository {
    private final Context context;
    private final ObjectMapper objectMapper;

    public JsonInspectionReportRepository(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
        //this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public Map<String, List<InspectionReport>> getInspections() throws RepositoryReadError {
        String jsonString;

        try {
            jsonString = Utils.readJSONFromStorage(this.context, Constants.INSPECTION_REPORT_FILE);
        } catch (IOException e) {
            try {
                jsonString = Utils.readJsonFromAssets(this.context, Constants.INSPECTION_REPORT_FILE);
            } catch (IOException ioException) {
                throw new RepositoryReadError(ioException.getMessage());
            }
        }

        TypeReference<Map<String, List<InspectionReport>>> mapTypeReference =
                new TypeReference<Map<String, List<InspectionReport>>>() {
                };

        try {
            return objectMapper.readValue(jsonString, mapTypeReference);
        } catch (JsonProcessingException e) {
            throw new RepositoryReadError(e.getMessage());
        }
    }

    @Override
    public Map<String, List<InspectionReport>> saveInspections(Map<String, List<InspectionReport>> inspections) throws RepositoryWriteError {
        try {
            String jsonString = this.objectMapper.writeValueAsString(inspections);
            Utils.writeJSONToStorage(this.context, Constants.INSPECTION_REPORT_FILE, jsonString);
            return inspections;
        } catch (IOException e) {
            throw new RepositoryWriteError(e.getMessage());
        }
    }
}
