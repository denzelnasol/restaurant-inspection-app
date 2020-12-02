package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.group11.cmpt276_project.exception.RepositoryReadError;
import com.group11.cmpt276_project.exception.RepositoryWriteError;
import com.group11.cmpt276_project.service.dto.InspectionReportDto;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.repository.IInspectionReportRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This class is a singleton that contains the inspection reports data provided. The class stores the data in
a Map to allow quick access without searching. The data is sorted in descending order during initialization
 */
public class InspectionReportsViewModel {

    private final MediatorLiveData<Map<String, List<InspectionReport>>> mReports;
    private LiveData<List<InspectionReport>> mData;
    private IInspectionReportRepository inspectionReportRepository;

    private final MutableLiveData<List<String>> newInspections;

    private InspectionReportsViewModel() {
        this.mReports = new MediatorLiveData<>();
        this.newInspections = new MutableLiveData<>();
    }

    private static class InspectionReportsViewModelHolder {
        private static final InspectionReportsViewModel INSTANCE = new InspectionReportsViewModel();
    }

    public static InspectionReportsViewModel getInstance() {
        return InspectionReportsViewModelHolder.INSTANCE;
    }

    public void init(IInspectionReportRepository jsonInspectionReportRepository) {
        this.inspectionReportRepository = jsonInspectionReportRepository;

        try {
            this.mData = this.inspectionReportRepository.getInspections();
        } catch (RepositoryReadError e) {
            this.mData = new MutableLiveData<>();
        }

        this.mReports.addSource(this.mData, (data) -> {

            if (data == null) return;

            Map<String, List<InspectionReport>> inspectionReports = new HashMap<>();

            for (InspectionReport inspectionReport : data) {
                inspectionReports.computeIfAbsent(inspectionReport.getTrackingNumber(), (key) -> new ArrayList<>()).add(inspectionReport);
            }

            this.mReports.setValue(inspectionReports);
        });
    }

    public void cleanUp() {
        this.mReports.removeSource(this.mData);
        this.mReports.setValue(null);
    }

    public LiveData<Map<String, List<InspectionReport>>> getReports() {
        return this.mReports;
    }

    public LiveData<List<String>> getNewInspections() {
        return this.newInspections;
    }

    public void save(List<InspectionReportDto> newReports) {
        try {
            List<InspectionReport> toAdd = new ArrayList<>();

            for (InspectionReportDto dto : newReports) {
                InspectionReport report = new InspectionReport.InspectionReportBuilder()
                        .withTrackingNumber(dto.getTrackingNumber())
                        .withHazardRating(dto.getHazardRating())
                        .withInspectionDate(dto.getInspectionDate())
                        .withInspectionType(dto.getInspectionType())
                        .withNumberCritical(dto.getNumberCritical())
                        .withNumberNonCritical(dto.getNumberNonCritical())
                        .withViolLump(dto.getViolLump())
                        .build();

                toAdd.add(report);
            }


            this.newInspections.postValue(this.inspectionReportRepository.saveInspections(toAdd));
        } catch (RepositoryWriteError repositoryWriteError) {
            repositoryWriteError.printStackTrace();
        }
    }
}
