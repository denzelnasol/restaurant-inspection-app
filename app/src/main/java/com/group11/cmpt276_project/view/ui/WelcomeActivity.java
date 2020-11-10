package com.group11.cmpt276_project.view.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.group11.cmpt276_project.R;

import com.group11.cmpt276_project.databinding.ActivityWelcomeBinding;
import com.group11.cmpt276_project.service.network.SurreyApiClient;
import com.group11.cmpt276_project.service.network.endpoints.DownloadDataSetService;
import com.group11.cmpt276_project.service.network.endpoints.GetDataSetService;
import com.group11.cmpt276_project.service.repository.IPreferenceRepository;
import com.group11.cmpt276_project.service.repository.impl.JsonInspectionReportRepository;
import com.group11.cmpt276_project.service.repository.impl.JsonRestaurantRepository;
import com.group11.cmpt276_project.service.repository.impl.JsonViolationRepository;
import com.group11.cmpt276_project.service.repository.impl.SharedPreferenceRepository;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;
import com.group11.cmpt276_project.viewmodel.ViolationsViewModel;
import com.group11.cmpt276_project.viewmodel.WelcomeViewModel;
import com.group11.cmpt276_project.viewmodel.factory.WelcomeViewModelFactory;

import retrofit2.Retrofit;

/**
 * This activity displays a welcome screen prior to showing the restaurant list
 */
public class WelcomeActivity extends AppCompatActivity {

    private final int TIMEOUT = 3000;

    private WelcomeViewModel welcomeViewModel;
    private ActivityWelcomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.init();
        this.bind();
        this.observe();
        this.welcomeViewModel.checkForUpdates();
    }

    public void observe() {
        this.welcomeViewModel.getShouldUpdate().observe(this, (data) -> {
            if (data) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.updates)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                welcomeViewModel.startDownload();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                moveToRestaurantList(250);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                this.moveToRestaurantList(TIMEOUT);
            }
        });
        this.welcomeViewModel.getUpdateDone().observe(this, (data) -> {
            if(data) {
                moveToRestaurantList(250);
            }
        });
        this.welcomeViewModel.getDownloadFailed().observe(this, (data) -> {
            if(data) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Download failed")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        this.welcomeViewModel.getIsCancelled().observe(this, (data) -> {
            if(data) {
                this.moveToRestaurantList(500);
            }
        });
    }

    public void onCancelClick() {
        this.welcomeViewModel.cancelDownload();
    }

    private void moveToRestaurantList(int timeOut) {
        new Handler().postDelayed(() -> {
            Intent intent = MainPageActivity.startActivity(this, null);
            startActivity(intent);
        }, timeOut);
    }

    private void init() {
        JsonRestaurantRepository jsonRestaurantRepository = new JsonRestaurantRepository(getApplicationContext());
        RestaurantsViewModel.getInstance().init(jsonRestaurantRepository);
        JsonInspectionReportRepository jsonInspectionReportRepository = new JsonInspectionReportRepository(getApplicationContext());
        InspectionReportsViewModel.getInstance().init(jsonInspectionReportRepository);
        JsonViolationRepository jsonViolationRepository = new JsonViolationRepository(getApplicationContext());
        ViolationsViewModel.getInstance().init(jsonViolationRepository);
    }

    private void bind() {
        Retrofit surreyApiClient = SurreyApiClient.getInstance();
        GetDataSetService apiService = surreyApiClient.create(GetDataSetService.class);
        DownloadDataSetService downloadService = surreyApiClient.create(DownloadDataSetService.class);
        IPreferenceRepository preferenceRepository = new SharedPreferenceRepository();
        WelcomeViewModelFactory viewModelFactory = new WelcomeViewModelFactory(apiService, downloadService, preferenceRepository, getApplicationContext());
        this.welcomeViewModel = new ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel.class);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        this.binding.setLifecycleOwner(this);
        this.binding.setWelcomeViewModel(this.welcomeViewModel);
        this.binding.setActivity(this);
    }


}