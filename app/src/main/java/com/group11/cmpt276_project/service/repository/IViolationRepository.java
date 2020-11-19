package com.group11.cmpt276_project.service.repository;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.Violation;

import java.util.Map;

//An interface describing what functions a ViolationRepository should have.
public interface IViolationRepository {

    Map<String, Violation> getViolations() throws RepositoryReadError;
    Map<String, Violation> saveViolations(Map<String, Violation> violations) throws RepositoryWriteError;
}
