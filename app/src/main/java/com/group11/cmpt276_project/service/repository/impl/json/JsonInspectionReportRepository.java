package com.group11.cmpt276_project.service.repository.impl.json;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.repository.IInspectionReportRepository;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* This class serves to load the inspection report json file
 * The get function will load the json file using getJsonFromAssets util function and implement
 * the string and the list as a map using Jackson
 * */

public class JsonInspectionReportRepository implements IInspectionReportRepository {
    private final Context context;
    private final ObjectMapper objectMapper;

    private MutableLiveData<List<InspectionReport>> inspectionReports;

    public JsonInspectionReportRepository(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public LiveData<List<InspectionReport>> getInspections() throws RepositoryReadError {

        if(inspectionReports != null) {
            return inspectionReports;
        }

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

        TypeReference<List<InspectionReport>> typeReference =
                new TypeReference<List<InspectionReport>>() {
                };

        try {
            List<InspectionReport> reports = objectMapper.readValue(jsonString, typeReference);

            Collections.sort(reports, (InspectionReport A, InspectionReport B) -> Integer.parseInt(B.getInspectionDate()) - Integer.parseInt(A.getInspectionDate()));

            this.inspectionReports = new MutableLiveData<>(reports);
            return inspectionReports;
        } catch (JsonProcessingException e) {
            throw new RepositoryReadError(e.getMessage());
        }
    }

    @Override
    public List<String> saveInspections(List<InspectionReport> inspections) throws RepositoryWriteError {
        try {
            String jsonString = this.objectMapper.writeValueAsString(inspections);
            Utils.writeJSONToStorage(this.context, Constants.INSPECTION_REPORT_FILE, jsonString);
            this.inspectionReports.postValue(inspections);
        } catch (IOException e) {
            throw new RepositoryWriteError(e.getMessage());
        }

        return new ArrayList<>();
    }
}
