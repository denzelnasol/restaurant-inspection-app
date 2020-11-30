package com.group11.cmpt276_project.service.repository;

import androidx.lifecycle.LiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Violation;

import java.util.List;
import java.util.Map;

//An interface describing what functions a ViolationRepository should have.
public interface IViolationRepository {

    LiveData<List<Violation>> getViolations() throws RepositoryReadError;
    void saveViolations(List<Violation> violations) throws RepositoryWriteError;
}
