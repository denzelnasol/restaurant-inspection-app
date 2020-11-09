package com.group11.cmpt276_project.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.group11.cmpt276_project.service.network.api.endpoints.GetDataSetService;
import com.group11.cmpt276_project.service.repository.IPreferenceRepository;
import com.group11.cmpt276_project.viewmodel.WelcomeViewModel;


public class WelcomeViewModelFactory implements ViewModelProvider.Factory {

    private final GetDataSetService service;
    private final IPreferenceRepository preferenceRepository;


    public WelcomeViewModelFactory(GetDataSetService service, IPreferenceRepository preferenceRepository) {
        this.service = service;
        this.preferenceRepository = preferenceRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WelcomeViewModel.class)) {
            return (T) new WelcomeViewModel(this.service, this.preferenceRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
