package com.group11.cmpt276_project.service.repository;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/* This class serves to load the inspection report json file
 * The get function will load the json file using getJsonFromAssets util function and implement
 * the string and the list as a map using Jackson
 * */

public class InspectionReportRepository {
    private final Context context;
    private final ObjectMapper objectMapper;

    public InspectionReportRepository(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public Map<String, List<InspectionReport>> get() throws IOException {
        String jsonString = Utils.getJsonFromAssets(this.context, Constants.INSPECTION_REPORT_FILE);
        TypeReference<Map<String, List<InspectionReport>>> mapTypeReference =
                new TypeReference<Map<String, List<InspectionReport>>>() {};
        return objectMapper.readValue(jsonString, mapTypeReference);
    }
}
