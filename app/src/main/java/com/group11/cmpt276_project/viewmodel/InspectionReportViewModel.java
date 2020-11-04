package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * An inspection report view model to set the visibility of an inspection
 */
public class InspectionReportViewModel extends ViewModel {

    private boolean[] isVisible;
    private MutableLiveData<boolean[]> mIsVisible;

    public InspectionReportViewModel(int size) {
        this.isVisible = new boolean[size];
        this.mIsVisible = new MutableLiveData<>(this.isVisible);
    }

    public LiveData<boolean[]> getIsVisibleData() {
        return this.mIsVisible;
    }

    public void setIsVisible(int index) {
        this.isVisible[index] = !this.isVisible[index];
        mIsVisible.setValue(this.isVisible);
    }
}
