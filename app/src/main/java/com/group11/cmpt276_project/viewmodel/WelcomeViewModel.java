package com.group11.cmpt276_project.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.databind.JsonNode;
import com.group11.cmpt276_project.service.network.api.endpoints.GetDataSetService;
import com.group11.cmpt276_project.service.repository.IPreferenceRepository;
import com.group11.cmpt276_project.utils.Constants;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeViewModel extends ViewModel {

    public MutableLiveData<Integer> mProgress;
    public MutableLiveData<Boolean> mIsDownloading;

    private MediatorLiveData<Boolean> mShouldUpdate;

    private MutableLiveData<String> mLastModifiedRestaurants;
    private MutableLiveData<String> mLastModifiedInspections;

    private GetDataSetService service;
    private IPreferenceRepository preferenceRepository;

    private String restaurantsDownloadLink;
    private String inspectionsDownloadLink;

    private String lastUpdateInspection;
    private String lastUpdateRestaurant;
    private String lastUpdate;

    private boolean shouldUpdateInspection;
    private boolean shouldUpdateRestaurants;

    public WelcomeViewModel(GetDataSetService service, IPreferenceRepository preferenceRepository) {
        this.service = service;
        this.mProgress = new MutableLiveData<>(0);
        this.mIsDownloading = new MutableLiveData<>(false);
        this.mLastModifiedRestaurants = new MutableLiveData<>();
        this.mLastModifiedInspections = new MutableLiveData<>();
        this.mShouldUpdate = new MediatorLiveData<>();
        this.mShouldUpdate.addSource(this.mLastModifiedInspections, (data) -> {
            this.update();
        });
        this.mShouldUpdate.addSource(this.mLastModifiedRestaurants, (data) -> {
            this.update();
        });
        this.preferenceRepository = preferenceRepository;

        this.lastUpdateInspection = this.preferenceRepository.getPreference(Constants.LAST_UPDATE_INSPECTION);
        this.lastUpdateRestaurant = this.preferenceRepository.getPreference(Constants.LAST_UPDATE_RESTAURANT);
        this.lastUpdate = this.preferenceRepository.getPreference(Constants.LAST_UPDATE);
    }


    public void checkForUpdates() {

        boolean pastTwentyHours = true;

        if (this.lastUpdate != null && !this.lastUpdate.isEmpty()) {
            LocalDateTime lastUpdateTime = LocalDateTime.parse(this.lastUpdate);
            LocalDateTime now = LocalDateTime.now();
            pastTwentyHours = ChronoUnit.HOURS.between(lastUpdateTime, now) >= 20;
        }

        if (pastTwentyHours) {
            Call<JsonNode> checkInspections = this.service.getDataSet("fraser-health-restaurant-inspection-reports");
            checkInspections.enqueue(new Callback<JsonNode>() {
                @Override
                public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                    if (response.isSuccessful()) {
                        JsonNode jsonNode = response.body();

                        restaurantsDownloadLink = jsonNode.get("result").get("resources").get(0).get("original_url").toString();
                        mLastModifiedRestaurants.setValue(jsonNode.get("result").get("metadata_modified").toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonNode> call, Throwable t) {
                }
            });
            Call<JsonNode> checkRestaurants = this.service.getDataSet("restaurants");
            checkRestaurants.enqueue(new Callback<JsonNode>() {
                @Override
                public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                    if (response.isSuccessful()) {
                        JsonNode jsonNode = response.body();

                        inspectionsDownloadLink = jsonNode.get("result").get("resources").get(0).get("original_url").toString();
                        mLastModifiedInspections.setValue(jsonNode.get("result").get("metadata_modified").toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonNode> call, Throwable t) {

                }
            });
        } else {
            this.mShouldUpdate.setValue(false);
        }
    }

    private void update() {
        String lastModifiedRestaurants = this.mLastModifiedRestaurants.getValue();
        String lastModifiedInspections = this.mLastModifiedInspections.getValue();


        if (lastModifiedInspections != null && lastModifiedRestaurants != null) {

            this.shouldUpdateInspection = !lastModifiedInspections.equals(this.lastUpdateInspection);
            this.shouldUpdateRestaurants = !lastModifiedRestaurants.equals(this.lastUpdateRestaurant);

            this.mShouldUpdate.setValue(this.shouldUpdateInspection || this.shouldUpdateRestaurants);
        }
    }


    public MediatorLiveData<Boolean> getShouldUpdate() {
        return mShouldUpdate;
    }

    public String getRestaurantsDownloadLink() {
        return restaurantsDownloadLink;
    }

    public String getInspectionsDownloadLink() {
        return inspectionsDownloadLink;
    }

    public boolean isShouldUpdateInspection() {
        return shouldUpdateInspection;
    }

    public boolean isShouldUpdateRestaurants() {
        return shouldUpdateRestaurants;
    }
}
