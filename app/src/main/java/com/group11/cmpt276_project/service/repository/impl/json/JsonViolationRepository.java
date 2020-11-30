package com.group11.cmpt276_project.service.repository.impl.json;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
import java.util.List;
import java.util.Map;

/**This class serves to load the violations json file
 * The get function will load the json file using getJsonFromAssets util function and maps it to a string and
 * list using Jackson
 */
public class JsonViolationRepository implements IViolationRepository {
    private Context context;
    private ObjectMapper objectMapper;

    private MutableLiveData<List<Violation>> violations;

    public JsonViolationRepository(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public LiveData<List<Violation>> getViolations() throws RepositoryReadError {

        if(this.violations != null) {
            return this.violations;
        }

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

        TypeReference<List<Violation>> typeReference = new TypeReference<List<Violation>>() {};

        try {
            this.violations = new MutableLiveData<>(objectMapper.readValue(jsonString, typeReference));
            return this.violations;
        } catch (JsonProcessingException e) {
            throw new RepositoryReadError(e.getMessage());
        }
    }

    @Override
    public void saveViolations(List<Violation> violations) throws RepositoryWriteError {
        try {
            String jsonString = this.objectMapper.writeValueAsString(violations);
            Utils.writeJSONToStorage(this.context, Constants.VIOLATION_FILE, jsonString);
            this.violations.postValue(violations);
        } catch (IOException e) {
            throw new RepositoryWriteError(e.getMessage());
        }
    }
}
