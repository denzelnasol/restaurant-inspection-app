package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.dto.ViolationDto;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.service.repository.IViolationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A singleton class that contains a map of all violations given. Violations are accessed through
 * respective IDs
 */
public class ViolationsViewModel {
    private LiveData<List<Violation>> mData;
    private final MediatorLiveData<Map<String, Violation>> mViolations;

    private IViolationRepository violationRepository;

    private ViolationsViewModel() {
        this.mViolations = new MediatorLiveData<>();
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
                this.mData = this.violationRepository.getViolations();
            } catch (RepositoryReadError e) {
                this.mData = new MutableLiveData<>();
            }

            this.mViolations.addSource(this.mData, (data) -> {
                this.createViolationMap(data);
            });
        }
    }

    public LiveData<Map<String, Violation>> getViolations() {
        return this.mViolations;
    }

    public void save(List<ViolationDto> newViolations) {
        try {

            List<Violation> toAdd = new ArrayList<>();

            for(ViolationDto dto : newViolations) {
                Violation violation = new Violation.ViolationBuilder()
                        .withDetails(dto.getDetails())
                        .withId(dto.getId())
                        .withStatus(dto.getStatus())
                        .withType(dto.getType())
                        .build();

                toAdd.add(violation);
            }
            this.violationRepository.saveViolations(toAdd);
        } catch (RepositoryWriteError repositoryWriteError) {
            repositoryWriteError.printStackTrace();
        }
    }

    public void updateLanguage() {
        this.mViolations.removeSource(this.mData);
        try {
            this.mData = this.violationRepository.getViolations();
            this.mViolations.addSource(this.mData, (data) -> {
                this.createViolationMap(data);
            });
        } catch (RepositoryReadError repositoryReadError) {
            repositoryReadError.printStackTrace();
        }
    }

    public void createViolationMap(List<Violation> data) {
        if(data == null) return;

        Map<String, Violation> violations = new HashMap<>();

        for(Violation violation : data) {
            violations.put(violation.getId(), violation);
        }

        this.mViolations.setValue(violations);
    }
}
