package com.group11.cmpt276_project.service.repository;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.InspectionReport;

import java.util.List;
import java.util.Map;

//An interface describing what functions a InspectionRepository should have.
public interface IInspectionReportRepository {

    Map<String, List<InspectionReport>> getInspections() throws RepositoryReadError;
    Map<String, List<InspectionReport>>  saveInspections(Map<String, List<InspectionReport>> inspections) throws RepositoryWriteError;

}
