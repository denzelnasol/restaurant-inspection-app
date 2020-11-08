package com.group11.cmpt276_project.service.repository.impl;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.service.repository.IViolationRepository;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.Map;

/**This class serves to load the violations json file
 * The get function will load the json file using getJsonFromAssets util function and maps it to a string and
 * list using Jackson
 */
public class JsonViolationRepository implements IViolationRepository {
    private Context context;
    private ObjectMapper objectMapper;

    public JsonViolationRepository(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Map<String, Violation> getViolations() throws RepositoryReadError {
        String jsonString;
        try {
            jsonString = Utils.readJSONFromStorage(this.context, Constants.VIOLATION_FILE);
        } catch (IOException e) {
            try {
                jsonString = Utils.readJsonFromAssets(this.context, Constants.VIOLATION_FILE);
            } catch (IOException ioException) {
                throw new RepositoryReadError(ioException.getMessage());
            }
        }

        TypeReference<Map<String, Violation>> mapTypeReference = new TypeReference<Map<String, Violation>>() {};

        try {
            return objectMapper.readValue(jsonString, mapTypeReference);
        } catch (JsonProcessingException e) {
            throw new RepositoryReadError(e.getMessage());
        }
    }

    @Override
    public Map<String, Violation> saveViolations(Map<String, Violation> violations) throws RepositoryWriteError {
        try {
            String jsonString = this.objectMapper.writeValueAsString(violations);
            Utils.writeJSONToStorage(this.context, Constants.VIOLATION_FILE, jsonString);
            return violations;
        } catch (IOException e) {
            throw new RepositoryWriteError(e.getMessage());
        }
    }
}
