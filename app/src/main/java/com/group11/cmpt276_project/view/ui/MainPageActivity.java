package com.group11.cmpt276_project.view.ui;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.ActivityMainPageBinding;
import com.group11.cmpt276_project.service.model.GPSCoordiantes;
import com.group11.cmpt276_project.view.adapter.TabAdapter;
import com.group11.cmpt276_project.view.ui.fragment.MapFragment;
import com.group11.cmpt276_project.view.ui.fragment.RestaurantListFragment;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.MainPageViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;
import com.group11.cmpt276_project.viewmodel.ViolationsViewModel;

public class MainPageActivity extends FragmentActivity {

    private static final String SHOULD_UPDATE = "shouldUpdate";
    private static final String GPS_COORDINATES = "gpsCoordiantes";

    private final int[] tabs = new int[]{R.string.map, R.string.list};

    private ViewPager2 viewPager;

    private MainPageViewModel mainPageViewModel;
    private ActivityMainPageBinding binding;

    private boolean shouldUpdate;
    private GPSCoordiantes gpsCoordinates;

    public static Intent startActivity(Context context, boolean shouldUpdate, GPSCoordiantes gpsCoordiantes) {
        Intent intent = new Intent(context, MainPageActivity.class);
        intent.putExtra(SHOULD_UPDATE, shouldUpdate);
        intent.putExtra(GPS_COORDINATES, gpsCoordiantes);
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RestaurantsViewModel.getInstance().save();
        InspectionReportsViewModel.getInstance().save();
        ViolationsViewModel.getInstance().save();
        this.finishAffinity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        this.bind();
        this.setUpViewPager();
        this.setUpTabs();
    }

    public GPSCoordiantes getGpsCoordinates() {
        return this.gpsCoordinates;
    }

    private void bind() {
        this.mainPageViewModel = MainPageViewModel.getInstance();
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main_page);

        Intent intent = getIntent();

        this.shouldUpdate = intent.getBooleanExtra(SHOULD_UPDATE, false);
        this.gpsCoordinates = intent.getParcelableExtra(GPS_COORDINATES);
    }

    private void setUpViewPager() {
        this.viewPager = this.binding.mainPageViewPager;
        TabAdapter tabAdapter = new TabAdapter(this);
        tabAdapter.addFragment(new MapFragment());
        tabAdapter.addFragment(new RestaurantListFragment());

        this.viewPager.setUserInputEnabled(false);
        this.viewPager.setAdapter(tabAdapter);
        this.viewPager.setCurrentItem(mainPageViewModel.getSelectedTab());
        this.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mainPageViewModel.setSelectedTabTab(position);
            }
        });
    }

    private void setUpTabs() {
        TabLayout tabLayout = this.binding.mainPageTabs;
        new TabLayoutMediator(tabLayout, this.viewPager, (tab, position) -> {
            tab.setText(tabs[position]);
        }).attach();
    }


}