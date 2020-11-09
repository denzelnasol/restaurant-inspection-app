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
import com.group11.cmpt276_project.service.network.api.SurreyApiClient;
import com.group11.cmpt276_project.service.network.api.endpoints.GetDataSetService;
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
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                moveToRestaurantList(0);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                this.moveToRestaurantList(TIMEOUT);
            }
        });
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
        GetDataSetService service = SurreyApiClient.getInstance().create(GetDataSetService.class);
        IPreferenceRepository preferenceRepository = new SharedPreferenceRepository();
        WelcomeViewModelFactory viewModelFactory = new WelcomeViewModelFactory(service, preferenceRepository);
        this.welcomeViewModel = new ViewModelProvider(this, viewModelFactory).get(WelcomeViewModel.class);

        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
        this.binding.setLifecycleOwner(this);
        binding.setWelcomeViewModel(this.welcomeViewModel);
    }


}