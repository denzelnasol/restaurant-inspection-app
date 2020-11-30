package com.group11.cmpt276_project.view.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.FragmentRestaurantListBinding;
import com.group11.cmpt276_project.view.adapter.RestaurantAdapter;
import com.group11.cmpt276_project.view.ui.RestaurantDetailActivity;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.MainPageViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantListFragmentViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;
import com.group11.cmpt276_project.viewmodel.factory.RestaurantListFragmentViewModelFactory;

import java.util.HashMap;

import java.util.Map;

/**
 * This fragment displays the restaurant list
 */
public class RestaurantListFragment extends Fragment {

    private RestaurantsViewModel restaurantsViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;

    private RestaurantListFragmentViewModel restaurantListFragmentViewModel;

    private FragmentRestaurantListBinding binding;

    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_restaurant_list, container, false);
        this.bind();
        return this.binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.observeRestaurants();
    }

    private void bind() {
        this.restaurantsViewModel = RestaurantsViewModel.getInstance();
        this.inspectionReportsViewModel = InspectionReportsViewModel.getInstance();

        RestaurantListFragmentViewModelFactory restaurantListFragmentViewModelFactory = new RestaurantListFragmentViewModelFactory(
                this.restaurantsViewModel.getRestaurants(),
                this.inspectionReportsViewModel.getReports()
        );

        this.restaurantListFragmentViewModel = new ViewModelProvider(this, restaurantListFragmentViewModelFactory).get(RestaurantListFragmentViewModel.class);
    }

    private void observeRestaurants() {
        this.restaurantListFragmentViewModel.getData().observe(getActivity(), (data) -> {

            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(data.first, data.second, new RestaurantItemOnClickTrackingNumber());

            RecyclerView restaurantList = this.binding.restaurantList;

            restaurantList.setLayoutManager(new LinearLayoutManager(getContext()));
            restaurantList.setAdapter(restaurantAdapter);
        });
    }

    private class RestaurantItemOnClickTrackingNumber implements RestaurantAdapter.IRestaurantItemOnClick {

        @Override
        public void onItemClick(String trackingNumber) {
            Intent intent = RestaurantDetailActivity.startActivity(getActivity(), trackingNumber);
            startActivity(intent);
        }
    }
}