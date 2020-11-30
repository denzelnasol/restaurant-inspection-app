package com.group11.cmpt276_project.viewmodel;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An inspection report view model to set the visibility of an inspection
 */
public class InspectionReportDetailViewModel extends ViewModel {

    private MediatorLiveData<Pair<boolean[], List<Violation>>> mDetails;

    private boolean[] isVisible;
    private List<Violation> reportViolations;

    private String trackingNumber;
    private int index;

    private Map<String, List<InspectionReport>> inspectionReports;
    private Map<String, Violation> violations;

    public InspectionReportDetailViewModel(LiveData<Map<String, List<InspectionReport>>> inspectionReports, LiveData<Map<String, Violation>> violations, String trackingNumber, int index) {

        this.trackingNumber = trackingNumber;
        this.index = index;

        this.mDetails = new MediatorLiveData<>();
        this.mDetails.addSource(inspectionReports, (data) -> {
            this.inspectionReports = data;
            this.mergeSources();
        });
        this.mDetails.addSource(violations, (data) -> {
            this.violations = data;
            this.mergeSources();
        });
    }

    public LiveData<Pair<boolean[], List<Violation>>> getData() {
        return this.mDetails;
    }

    public void setIsVisible(int index) {
        this.isVisible[index] = !this.isVisible[index];
        this.mDetails.setValue(new Pair<>(this.isVisible, this.reportViolations));
    }


    private void mergeSources() {
        if(this.inspectionReports != null && this.violations != null) {

            InspectionReport report = this.inspectionReports.get(trackingNumber).get(index);

            List<String> violIds = report.getViolLump();

            this.isVisible = new boolean[violIds.size()];
            this.reportViolations = new ArrayList<>();

            for(String violId : violIds) {
                this.reportViolations.add(this.violations.get(violId));
            }

            this.mDetails.setValue(new Pair<>(this.isVisible, this.reportViolations));
        }
    }
}
