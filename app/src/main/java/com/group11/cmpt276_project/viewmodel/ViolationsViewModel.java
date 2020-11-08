package com.group11.cmpt276_project.viewmodel;

import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.service.repository.impl.JsonViolationRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class that contains a map of all violations given. Violations are accessed through
 * respective IDs
 */
public class ViolationsViewModel {
    private Map<String, Violation> violations;
    private JsonViolationRepository jsonViolationRepository;

    private ViolationsViewModel() {}

    private static class ViolationsViewModelHolder {
        private static ViolationsViewModel INSTANCE = new ViolationsViewModel();
    }

    public static ViolationsViewModel getInstance() {
        return ViolationsViewModelHolder.INSTANCE;
    }

    public void init(JsonViolationRepository jsonViolationRepository) {
        if (this.jsonViolationRepository == null) {
            this.jsonViolationRepository = jsonViolationRepository;

            try {
                this.violations = this.jsonViolationRepository.getFromAssets();
            }
            catch (IOException e) {
                this.violations = new HashMap<>();
            }
        }
    }

    public Violation get(String id) {
        return violations.get(id);
    }
}
