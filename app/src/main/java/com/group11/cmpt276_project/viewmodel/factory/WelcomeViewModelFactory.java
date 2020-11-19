package com.group11.cmpt276_project.viewmodel.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group11.cmpt276_project.service.network.endpoints.DownloadDataSetService;
import com.group11.cmpt276_project.service.network.endpoints.GetDataSetService;
import com.group11.cmpt276_project.service.repository.IPreferenceRepository;
import com.group11.cmpt276_project.viewmodel.WelcomeViewModel;

import java.io.File;

/**
 * This factory is used to create a WelcomeViewModel. It is is needed because the constructor
 * has arguments
 */
public class WelcomeViewModelFactory implements ViewModelProvider.Factory {

    private final GetDataSetService apiService;
    private final DownloadDataSetService downloadService;
    private final IPreferenceRepository preferenceRepository;
    private final Context context;


    public WelcomeViewModelFactory(GetDataSetService apiService, DownloadDataSetService downloadService, IPreferenceRepository preferenceRepository, Context context) {
        this.apiService = apiService;
        this.downloadService = downloadService;
        this.preferenceRepository = preferenceRepository;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WelcomeViewModel.class)) {
            return (T) new WelcomeViewModel(this.apiService, this.downloadService, this.preferenceRepository, this.context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
