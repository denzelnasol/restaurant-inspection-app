package com.group11.cmpt276_project.view.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.FragmentRestaurantListBinding;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.view.adapter.RestaurantAdapter;
import com.group11.cmpt276_project.view.adapter.interfaces.IItemOnClick;
import com.group11.cmpt276_project.view.ui.RestaurantDetailActivity;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;

import java.util.ArrayList;
import java.util.List;


public class RestaurantListFragment extends Fragment {

    private RestaurantsViewModel restaurantsViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;

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
    }

    private void observeRestaurants() {
        this.restaurantsViewModel.get().observe(getActivity(), (data) -> {
            List<InspectionReport> reports = new ArrayList<>();

            for(Restaurant restaurant : data) {
                reports.add(this.inspectionReportsViewModel.getMostRecentReport(restaurant.getTrackingNumber()));
            }

            RestaurantAdapter restaurantAdapter = new RestaurantAdapter(data, reports, new RestaurantItemOnClick());

            RecyclerView restaurantList = this.binding.restaurantList;

            restaurantList.setLayoutManager(new LinearLayoutManager(getContext()));
            restaurantList.setAdapter(restaurantAdapter);
        });
    }

    private class RestaurantItemOnClick implements IItemOnClick {

        @Override
        public void onItemClick(int position) {
            Intent intent = RestaurantDetailActivity.startActivity(getActivity(), position);
            startActivity(intent);
        }
    }
}