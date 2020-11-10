package com.group11.cmpt276_project.viewmodel;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.service.repository.IViolationRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * A singleton class that contains a map of all violations given. Violations are accessed through
 * respective IDs
 */
public class ViolationsViewModel {
    private Map<String, Violation> violations;
    private IViolationRepository violationRepository;

    private ViolationsViewModel() {
    }

    private static class ViolationsViewModelHolder {
        private static ViolationsViewModel INSTANCE = new ViolationsViewModel();
    }

    public static ViolationsViewModel getInstance() {
        return ViolationsViewModelHolder.INSTANCE;
    }

    public void init(IViolationRepository jsonViolationRepository) {
        if (this.violationRepository == null) {
            this.violationRepository = jsonViolationRepository;

            try {
                this.violations = this.violationRepository.getViolations();
            } catch (RepositoryReadError e) {
                this.violations = new HashMap<>();
            }
        }
    }

    public Violation get(String id) {
        return violations.getOrDefault(id, new Violation());
    }

    public void save() {
        try {
            this.violationRepository.saveViolations(this.violations);
        } catch (RepositoryWriteError repositoryWriteError) {
            repositoryWriteError.printStackTrace();
        }
    }
}
