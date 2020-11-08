package com.group11.cmpt276_project.service.repository.impl;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.utils.Constants;
import com.group11.cmpt276_project.utils.Utils;

import java.io.IOException;
import java.util.Map;

/**This class serves to load the violations json file
 * The get function will load the json file using getJsonFromAssets util function and maps it to a string and
 * list using Jackson
 */
public class JsonViolationRepository {
    private Context context;
    private ObjectMapper objectMapper;

    public JsonViolationRepository(Context context) {
        this.context = context;
        this.objectMapper = new ObjectMapper();
    }

    public Map<String, Violation> getFromAssets() throws IOException {
        String jsonString = Utils.readJsonFromAssets(this.context, Constants.VIOLATION_FILE);
        TypeReference<Map<String, Violation>> mapTypeReference = new TypeReference<Map<String, Violation>>() {};
        return objectMapper.readValue(jsonString, mapTypeReference);
    }
}
