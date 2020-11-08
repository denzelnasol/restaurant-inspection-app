package com.group11.cmpt276_project.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group11.cmpt276_project.viewmodel.InspectionReportDetailViewModel;

/**
 * This factory is used to create a InspectionReportViewModel. It is is needed because the constructor
 * has arguments
 */
public class InspectionReportViewModelFactory implements ViewModelProvider.Factory {

    private final int size;

    public InspectionReportViewModelFactory(int size) {
        this.size = size;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InspectionReportDetailViewModel.class)) {
            return (T) new InspectionReportDetailViewModel(this.size);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
