package com.group11.cmpt276_project.service.repository;

import androidx.lifecycle.LiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.model.InspectionReport;

import java.util.List;

//An interface describing what functions a InspectionRepository should have.
public interface IInspectionReportRepository {

    LiveData<List<InspectionReport>> getInspections() throws RepositoryReadError;

    List<InspectionReport> saveInspections(List<InspectionReport> inspections) throws RepositoryWriteError;
}
