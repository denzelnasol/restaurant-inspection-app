package com.group11.cmpt276_project.viewmodel;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Pair;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fasterxml.jackson.databind.JsonNode;
import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.network.endpoints.DownloadDataSetService;
import com.group11.cmpt276_project.service.network.endpoints.GetDataSetService;
import com.group11.cmpt276_project.service.repository.IPreferenceRepository;
import com.group11.cmpt276_project.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeViewModel extends ViewModel {

    private final String RESTAURANT_CSV = "restaurant.csv";
    private final String INSPECTION_CSV = "inspection.csv";
    private final String RESTAURANT = "restaurant";
    private final String INSPECTION = "inspection";

    public MutableLiveData<Integer> mProgress;
    public MutableLiveData<Boolean> mIsDownloading;
    public MutableLiveData<Boolean> mCanCancel;
    public MutableLiveData<String> mDownloadText;

    private final MediatorLiveData<Boolean> mShouldUpdate;
    private final MediatorLiveData<Boolean> mUpdateDone;

    private final MutableLiveData<String> mLastModifiedRestaurants;
    private final MutableLiveData<String> mLastModifiedInspections;

    private final MutableLiveData<Boolean> mDownloadFailed;
    private final MutableLiveData<Boolean> mDownloadDone;
    private MutableLiveData<Boolean> mIsCancelled;

    private final GetDataSetService apiService;
    private final DownloadDataSetService downloadService;
    private final IPreferenceRepository preferenceRepository;

    private String restaurantsDownloadUrl;
    private String inspectionsDownloadUrl;

    private final String lastUpdateInspection;
    private final String lastUpdateRestaurant;
    private final String lastUpdate;

    private Context context;

    private AsyncTask<ResponseBody, Pair<Integer, Long>, String> currentTask;
    private Call<ResponseBody> currentCall;



    private long contentRangeFileSize;

    private boolean shouldUpdateInspection;
    private boolean shouldUpdateRestaurants;

    public WelcomeViewModel(GetDataSetService apiService, DownloadDataSetService downloadService, IPreferenceRepository preferenceRepository, Context context) {
        this.context = context;
        this.apiService = apiService;
        this.downloadService = downloadService;

        this.mIsCancelled = new MutableLiveData<>(false);
        this.mDownloadText = new MutableLiveData<>("");
        this.mProgress = new MutableLiveData<>(0);
        this.mIsDownloading = new MutableLiveData<>(false);
        this.mCanCancel = new MutableLiveData<>(false);
        this.mLastModifiedRestaurants = new MutableLiveData<>();
        this.mLastModifiedInspections = new MutableLiveData<>();
        this.mDownloadFailed = new MutableLiveData<>(false);
        this.mDownloadDone = new MutableLiveData<>(false);

        this.mUpdateDone = new MediatorLiveData<>();
        this.mUpdateDone.addSource(this.mDownloadDone, (data) -> {
            this.finalizeUpdate();
        });
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

    public void cancelDownload() {
        if(this.currentCall != null) {
            this.mIsCancelled.setValue(true);
            this.currentCall.cancel();
        }
    }

    public void checkForUpdates() {

        boolean pastTwentyHours = true;

        if (this.lastUpdate != null && !this.lastUpdate.isEmpty()) {
            LocalDateTime lastUpdateTime = LocalDateTime.parse(this.lastUpdate);
            LocalDateTime now = LocalDateTime.now();
            pastTwentyHours = ChronoUnit.HOURS.between(lastUpdateTime, now) >= 20;
        }

        if (pastTwentyHours) {
            Call<JsonNode> checkInspections = this.apiService.getDataSet("fraser-health-restaurant-inspection-reports");
            checkInspections.enqueue(new Callback<JsonNode>() {
                @Override
                public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                    if (response.isSuccessful()) {
                        JsonNode jsonNode = response.body();

                        String fullLink = jsonNode.get("result").get("resources").get(0).get("original_url").toString();

                        inspectionsDownloadUrl = fullLink.replace("\"", "").split(Constants.BASE_URL)[1];
                        mLastModifiedInspections.setValue(jsonNode.get("result").get("metadata_modified").toString());
                    }
                }

                @Override
                public void onFailure(Call<JsonNode> call, Throwable t) {
                }
            });
            Call<JsonNode> checkRestaurants = this.apiService.getDataSet("restaurants");
            checkRestaurants.enqueue(new Callback<JsonNode>() {
                @Override
                public void onResponse(Call<JsonNode> call, Response<JsonNode> response) {
                    if (response.isSuccessful()) {
                        JsonNode jsonNode = response.body();

                        String fullLink = jsonNode.get("result").get("resources").get(0).get("original_url").toString();

                        restaurantsDownloadUrl = fullLink.replace("\"", "").split(Constants.BASE_URL)[1];
                        mLastModifiedRestaurants.setValue(jsonNode.get("result").get("metadata_modified").toString());
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

    public void startDownload() {
        this.mIsDownloading.setValue(true);
        this.mCanCancel.setValue(true);

        if (this.shouldUpdateRestaurants) {
            this.downloadRestaurant();
            return;
        }

        this.downloadInspections();
    }

    private void finalizeUpdate() {
        this.mCanCancel.setValue(false);
        this.mDownloadText.setValue(context.getString(R.string.finalize_updates));
    }

    private void downloadRestaurant() {
        this.mProgress.setValue(0);
        this.mDownloadText.setValue(this.context.getString(R.string.start_download, RESTAURANT));
        this.currentCall = this.downloadService.downloadFileByUrl(this.restaurantsDownloadUrl);
        this.currentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
                    contentRangeFileSize = getFileSize(headers);
                    currentTask = new DownloadRestaurantTask();
                    currentTask.execute(response.body());
                } else {
                    mDownloadFailed.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(call.isCanceled()) {
                    return;
                }
                mDownloadFailed.setValue(true);
            }
        });
    }

    private void downloadInspections() {
        this.mProgress.setValue(0);
        this.mDownloadText.setValue(this.context.getString(R.string.start_download, INSPECTION));
        this.currentCall = this.downloadService.downloadFileByUrl(this.inspectionsDownloadUrl);
        this.currentCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Headers headers = response.headers();
                    contentRangeFileSize = getFileSize(headers);
                    currentTask = new DownloadInspectionTask();
                    currentTask.execute(response.body());
                } else {
                    mDownloadFailed.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(call.isCanceled()) {
                    return;
                }
                mDownloadFailed.setValue(true);
            }
        });
    }

    private long getFileSize(Headers headers) {
        if (headers == null) {
            return -1;
        }

        String contentRange = headers.get("Content-Range");

        if (contentRange == null || contentRange.isEmpty()) {
            return -1;
        }

        String[] contentRangeSplit = contentRange.split("/");

        if (contentRangeSplit.length < 2) {
            return -1;
        }

        return Long.parseLong(contentRangeSplit[1]);
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

    public MutableLiveData<Boolean> getDownloadFailed() {
        return mDownloadFailed;
    }

    public MutableLiveData<Boolean> getUpdateDone() {
        return mUpdateDone;
    }

    public MutableLiveData<Boolean> getIsCancelled() {
        return mIsCancelled;
    }

    private class DownloadRestaurantTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, String> {

        @Override
        protected String doInBackground(ResponseBody... responseBodies) {
            saveOnDisk(responseBodies[0], RESTAURANT_CSV);
            return null;
        }

        @Override
        protected void onProgressUpdate(Pair<Integer, Long>... values) {
            super.onProgressUpdate(values);

            double current = values[0].first;
            long total = values[0].second;

            doProgressUpdate(current, total, RESTAURANT);

            if (current == 100) {
                if (shouldUpdateInspection) {
                    new Handler().postDelayed(() -> {
                        downloadInspections();
                    }, 1000);
                } else {
                    mDownloadDone.setValue(true);
                }
            }
        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            this.publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private class DownloadInspectionTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, String> {

        @Override
        protected String doInBackground(ResponseBody... responseBodies) {
            saveOnDisk(responseBodies[0], INSPECTION_CSV);
            return null;
        }

        @Override
        protected void onProgressUpdate(Pair<Integer, Long>... values) {
            super.onProgressUpdate(values);

            double current = values[0].first;
            long total = values[0].second;

            doProgressUpdate(current, total, INSPECTION);

            if (current == 100) {
                mDownloadDone.setValue(true);
            }
        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            this.publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    private void saveOnDisk(ResponseBody body, String fileName) {
        File destinationFile = new File(this.context.getFilesDir(), fileName);

        try (InputStream inputStream = body.byteStream();
             OutputStream outputStream = new FileOutputStream(destinationFile)) {

            byte data[] = new byte[4096];
            int bytesRead;
            int progress = 0;
            long fileSize = body.contentLength() == -1 ? this.contentRangeFileSize : body.contentLength();

            while ((bytesRead = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, bytesRead);
                progress += bytesRead;
                this.doSendProgressUpdate(fileName, progress, fileSize);
            }

            outputStream.flush();
            this.doSendProgressUpdate(fileName, 100, 100L);

        } catch (IOException e) {
            if(!this.mIsCancelled.getValue()) {
                e.printStackTrace();
                this.doSendProgressUpdate(fileName, -1, -1L);
            }
        }
    }


    private void doSendProgressUpdate(String fileName, int progress, long total) {
        Pair<Integer, Long> pair = new Pair<>(progress, total);

        if (RESTAURANT_CSV.equals(fileName)) {
            ((DownloadRestaurantTask)this.currentTask).doProgress(pair);
            return;
        }

        ((DownloadInspectionTask)this.currentTask).doProgress(pair);
    }

    private void doProgressUpdate(double current, long total, String type) {
        //System.out.println(total);
        if (current == -1) {
            this.mDownloadFailed.setValue(true);
            return;
        }

        if (total > 0) {
            int currentProgress = (int) ((current / total) * 100);

            this.mProgress.setValue(currentProgress);
            this.mDownloadText.setValue(this.context.getString(R.string.current_downloading, type, String.valueOf(currentProgress)));
        }
    }
}
