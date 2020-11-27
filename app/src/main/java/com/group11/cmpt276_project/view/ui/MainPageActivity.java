package com.group11.cmpt276_project.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.ActivityMainPageBinding;
import com.group11.cmpt276_project.service.model.GPSCoordiantes;
import com.group11.cmpt276_project.view.adapter.TabAdapter;
import com.group11.cmpt276_project.view.ui.fragment.MapFragment;
import com.group11.cmpt276_project.view.ui.fragment.RestaurantListFragment;
import com.group11.cmpt276_project.viewmodel.MainPageViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;
import com.group11.cmpt276_project.viewmodel.ViolationsViewModel;

//The main page of the app. It contains tabs for the map and list. On startup the map will be shown
public class MainPageActivity extends FragmentActivity {

    private static final String GPS_COORDINATES = "gpsCoordinates";

    private final int[] tabs = new int[]{R.string.map, R.string.list};

    private ViewPager2 viewPager;

    private MainPageViewModel mainPageViewModel;
    private ActivityMainPageBinding binding;

    private GPSCoordiantes gpsCoordinates;

    private RestaurantsViewModel restaurantsViewModel;

    public static Intent startActivity(Context context, GPSCoordiantes gpsCoordiantes) {
        Intent intent = new Intent(context, MainPageActivity.class);
        intent.putExtra(GPS_COORDINATES, gpsCoordiantes);
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ViolationsViewModel.getInstance().updateLanguage();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bind();
        this.setUpViewPager();
        this.setUpTabs();
        this.setUpSearch();
        this.observe();
    }

    private void observe() {
        this.mainPageViewModel.getExpandFilter().observe(this, (data) -> {
            this.binding.filterMenuContainer.setVisibility(data ? View.VISIBLE : View.GONE);
            this.binding.backdrop.setVisibility(data ? View.VISIBLE : View.GONE);
        });
    }

    public GPSCoordiantes getGpsCoordinates() {
        return this.gpsCoordinates;
    }

    private void bind() {
        this.mainPageViewModel = MainPageViewModel.getInstance();
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main_page);
        this.binding.setMainPageViewModel(this.mainPageViewModel);
        this.binding.setActivity(this);
        this.binding.setLifecycleOwner(this);

        this.restaurantsViewModel = RestaurantsViewModel.getInstance();

        Intent intent = getIntent();

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
                mainPageViewModel.setSelectedTab(position);
            }
        });
    }

    private void setUpSearch() {

        int searchEditTextId = this.binding.searchRestaurant.getContext().getResources()
                .getIdentifier("android:id/search_src_text", null, null);

        EditText searchEditText = this.binding.searchRestaurant.findViewById(searchEditTextId);
        searchEditText.setText(this.mainPageViewModel.getSearch());


        this.binding.searchRestaurant.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mainPageViewModel.setSearch(s);
                applySearch();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mainPageViewModel.setSearch(s);
                applySearch();
                return false;
            }
        });

        int searchCloseButtonId = this.binding.searchRestaurant.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);

        this.binding.searchRestaurant.findViewById(searchCloseButtonId).setOnClickListener((view) -> {

            EditText editText = this.binding.searchRestaurant.findViewById(searchEditTextId);
            editText.setText("");

            mainPageViewModel.setSearch("");
        });
    }

    private void applySearch() {
        this.mainPageViewModel.getFilter().observe(this, (data) -> {

            String name = this.mainPageViewModel.getSearch();
            this.restaurantsViewModel.search(name, data);

            this.mainPageViewModel.getFilter().removeObservers(this);
        });
    }

    private void setUpTabs() {
        TabLayout tabLayout = this.binding.mainPageTabs;
        new TabLayoutMediator(tabLayout, this.viewPager, (tab, position) -> {
            tab.setText(tabs[position]);
        }).attach();
    }

    public void openFilter() {
        this.mainPageViewModel.setExpandFilter(true);
    }

    public void closeFilter() {
        this.mainPageViewModel.setExpandFilter(false);
    }

    public void applyFilter() {
        this.applySearch();
        this.mainPageViewModel.setExpandFilter(false);
    }

    public void clearFilter() {
        this.mainPageViewModel.clearFilter();;
        this.applySearch();
    }

    public void noOp() {

    }

    public void clearAll() {
        this.mainPageViewModel.clearAll();
        this.restaurantsViewModel.clearSearch();
    }

    public void clearHazardLevel() {
        this.mainPageViewModel.clearHazardLevel();
        this.applySearch();
    }

    public void clearNumberCritical() {
        this.mainPageViewModel.clearNumberCritical();
        this.applySearch();
    }
}