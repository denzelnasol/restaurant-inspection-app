package com.group11.cmpt276_project.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Violation;
import com.group11.cmpt276_project.viewmodel.InspectionReportDetailViewModel;

import java.util.List;
import java.util.Map;

/**
 * This factory is used to create a InspectionReportViewModel. It is is needed because the constructor
 * has arguments
 */
public class InspectionReportDetailViewModelFactory implements ViewModelProvider.Factory {

    private final String trackingNumber;
    private final int index;
    private final LiveData<Map<String, List<InspectionReport>>> inspectionReports;
    private LiveData<Map<String, Violation>> violations;

    public InspectionReportDetailViewModelFactory(LiveData<Map<String, List<InspectionReport>>> inspectionReports, LiveData<Map<String, Violation>> violations, String trackingNumber, int index) {
        this.trackingNumber = trackingNumber;
        this.index = index;
        this.inspectionReports = inspectionReports;
        this.violations = violations;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InspectionReportDetailViewModel.class)) {
            return (T) new InspectionReportDetailViewModel(this.inspectionReports, this.violations, this.trackingNumber, this.index);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
