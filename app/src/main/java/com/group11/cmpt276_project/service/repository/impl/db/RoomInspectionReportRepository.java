package com.group11.cmpt276_project.service.repository.impl.db;

import androidx.lifecycle.LiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.dao.InspectionReportDao;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.repository.IInspectionReportRepository;

import java.util.List;

public class RoomInspectionReportRepository implements IInspectionReportRepository {

    private InspectionReportDao inspectionReportDao;
    private LiveData<List<InspectionReport>> reports;

    public RoomInspectionReportRepository(InspectionReportDao inspectionReportDao) {
        this.inspectionReportDao = inspectionReportDao;
        this.reports = inspectionReportDao.getAllInspectionReports();
    }

    @Override
    public LiveData<List<InspectionReport>> getInspections() throws RepositoryReadError {
        return this.reports;
    }

    @Override
    public List<String> saveInspections(List<InspectionReport> inspections) throws RepositoryWriteError {
        return this.inspectionReportDao.insertOrUpdate(inspections);
    }
}
