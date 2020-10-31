package com.group11.cmpt276_project.viewmodel;

import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.repository.InspectionReportRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
This class is a singleton that contains the inspection reports data provided. The class stores the data in
a Map to allow quick access without searching. The data is sorted in descending order during initialization
 */
public class InspectionReportsViewModel {

    private Map<String, List<InspectionReport>> reports;
    private InspectionReportRepository inspectionReportRepository;

    private InspectionReportsViewModel() {

    }

    private static class InspectionReportsViewModelHolder {
        private static InspectionReportsViewModel INSTANCE = new InspectionReportsViewModel();
    }

    public static InspectionReportsViewModel getInstance() {
        return InspectionReportsViewModelHolder.INSTANCE;
    }

    public void init(InspectionReportRepository inspectionReportRepository) {
        if(this.inspectionReportRepository == null) {
            this.inspectionReportRepository = inspectionReportRepository;

            try {
                this.reports = this.inspectionReportRepository.get();

                for(Map.Entry<String, List<InspectionReport>> entry : this.reports.entrySet()) {
                    Collections.sort(entry.getValue(), (InspectionReport A, InspectionReport B) -> Integer.parseInt(B.getInspectionDate()) - Integer.parseInt(A.getInspectionDate()));
                }
            } catch (IOException e) {
                System.out.println(e);
                this.reports = new HashMap<>();
            }
        }
    }

    public List<InspectionReport> getReports(String trackingNumber) {
        return this.reports.getOrDefault(trackingNumber, new ArrayList<>());
    }

    public InspectionReport getMostRecentReport(String trackingNumber) {
        if(!this.reports.containsKey(trackingNumber)) {
            return null;
        }

        return this.reports.get(trackingNumber).get(0);
    }

    public InspectionReport getByIndexAndTrackingNumbe(String trackingNumber, int index) {
        if(!this.reports.containsKey(trackingNumber)) {
            return null;
        }

        return this.reports.get(trackingNumber).get(index);
    }
}
