package com.group11.cmpt276_project.view.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.group11.cmpt276_project.R;

import com.group11.cmpt276_project.databinding.ActivityWelcomeBinding;
import com.group11.cmpt276_project.service.db.RestaurantDatabase;
import com.group11.cmpt276_project.service.network.SurreyApiClient;
import com.group11.cmpt276_project.service.network.endpoints.DownloadDataSetService;
import com.group11.cmpt276_project.service.network.endpoints.GetDataSetService;
import com.group11.cmpt276_project.service.repository.IInspectionReportRepository;
import com.group11.cmpt276_project.service.repository.IPreferenceRepository;
import com.group11.cmpt276_project.service.repository.IRestaurantRepository;
import com.group11.cmpt276_project.service.repository.IViolationRepository;
import com.group11.cmpt276_project.service.repository.impl.db.RoomInspectionReportRepository;
import com.group11.cmpt276_project.service.repository.impl.db.RoomRestaurantRepository;
import com.group11.cmpt276_project.service.repository.impl.db.RoomViolationRepository;
import com.group11.cmpt276_project.service.repository.impl.json.JsonInspectionReportRepository;
import com.group11.cmpt276_project.service.repository.impl.json.JsonRestaurantRepository;
import com.group11.cmpt276_project.service.repository.impl.json.JsonViolationRepository;
import com.group11.cmpt276_project.service.repository.impl.json.SharedPreferenceRepository;
import com.group11.cmpt276_project.utils.Constants;
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

    private final int TIMEOUT = 1500;
    private static final int LOCATION_PERMISSION_CODE = 100;

    private WelcomeViewModel welcomeViewModel;
    private ActivityWelcomeBinding binding;

    private InspectionReportsViewModel inspectionReportsViewModel;
    private RestaurantsViewModel restaurantsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
        }
        this.init();
        this.bind();
        this.observe();
        this.welcomeViewModel.testInternetConnectivity();
    }

    public void observe() {
        this.welcomeViewModel.getHasInternetConnection().observe(this, (data) -> {
            if (data) {
                welcomeViewModel.checkForUpdates();
            } else {
                moveToMainActivity(TIMEOUT);
            }
        });
        this.welcomeViewModel.getShouldUpdate().observe(this, (data) -> {
            if (data) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.updates)
                        .setPositiveButton(R.string.ok, (DialogInterface dialog, int id) -> {
                            welcomeViewModel.startDownload();
                            dialog.dismiss();
                        })
                        .setNegativeButton(R.string.cancel, (DialogInterface dialog, int id) -> {
                            dialog.dismiss();
                            this.moveToMainActivity(250);
                        });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            } else {
                this.moveToMainActivity(TIMEOUT);
            }
        });
        this.welcomeViewModel.getUpdateDone().observe(this, (data) -> {
            if (data) {
                moveToMainActivity(250);
            }
        });
        this.welcomeViewModel.getDownloadFailed().observe(this, (data) -> {
            if (data) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Download failed")
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
        this.welcomeViewModel.getIsCancelled().observe(this, (data) -> {
            if (data) {
                this.moveToMainActivity(500);
            }
        });
    }

    public void onCancelClick() {
        this.welcomeViewModel.cancelDownload();
    }

    private void moveToMainActivity(int timeOut) {
        new Handler().postDelayed(() -> {
            Intent intent = MainPageActivity.startActivity(this, null);
            startActivity(intent);
        }, timeOut);
    }

    private void init() {

        RestaurantDatabase restaurantDatabase = RestaurantDatabase.getDatabase(getApplicationContext());
        restaurantDatabase.restaurantDao().getAllRestaurants();
        restaurantDatabase.inspectionReportDao().getAllInspectionReports();
        restaurantDatabase.violationDao().getAllViolations();

        IRestaurantRepository restaurantRepository = new RoomRestaurantRepository(restaurantDatabase.restaurantDao());
        //IRestaurantRepository restaurantRepository = new JsonRestaurantRepository(getApplicationContext());
        this.restaurantsViewModel = RestaurantsViewModel.getInstance();
        this.restaurantsViewModel.init(restaurantRepository);

        IInspectionReportRepository inspectionReportRepository = new RoomInspectionReportRepository(restaurantDatabase.inspectionReportDao());
        //IInspectionReportRepository inspectionReportRepository = new JsonInspectionReportRepository(getApplicationContext());
        this.inspectionReportsViewModel = InspectionReportsViewModel.getInstance();
        this.inspectionReportsViewModel.init(inspectionReportRepository);

        IViolationRepository violationRepository = new RoomViolationRepository(restaurantDatabase.violationDao());
        //IViolationRepository violationRepository = new JsonViolationRepository(getApplicationContext());
        ViolationsViewModel.getInstance().init(violationRepository);
    }

    private void bind() {
        Retrofit surreyApiClient = SurreyApiClient.getInstance();
        GetDataSetService apiService = surreyApiClient.create(GetDataSetService.class);
        DownloadDataSetService downloadService = surreyApiClient.create(DownloadDataSetService.class);
        IPreferenceRepository preferenceRepository = new SharedPreferenceRepository(getApplicationContext(), Constants.SHARE_PREFERENCES_URL);
        WelcomeViewModelFactory viewModelFactory = new WelcomeViewModelFactory(apiService, downloadService, preferenceRepository, getApplicationContext());
        this.welcomeViewModel = new ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel.class);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        this.binding.setLifecycleOwner(this);
        this.binding.setWelcomeViewModel(this.welcomeViewModel);
        this.binding.setActivity(this);
    }
}