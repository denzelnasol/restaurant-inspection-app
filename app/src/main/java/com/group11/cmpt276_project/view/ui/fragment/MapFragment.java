package com.group11.cmpt276_project.view.ui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.databinding.FragmentMapBinding;
import com.group11.cmpt276_project.service.model.GPSCoordiantes;
import com.group11.cmpt276_project.view.ui.MainPageActivity;
import com.group11.cmpt276_project.viewmodel.InspectionReportsViewModel;
import com.group11.cmpt276_project.viewmodel.RestaurantsViewModel;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;

    private RestaurantsViewModel restaurantsViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;

    private GPSCoordiantes selected;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        this.bind();
        return this.binding.getRoot();
    }

    private void bind() {
        this.restaurantsViewModel = RestaurantsViewModel.getInstance();
        this.inspectionReportsViewModel = InspectionReportsViewModel.getInstance();
        MainPageActivity activity = (MainPageActivity) getActivity();
        this.selected = activity.getGpsCoordinates();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(this.selected != null) {
            this.binding.textview12.setText(getString(R.string.coord, String.valueOf(this.selected.getLatitude()), String.valueOf(this.selected.getLongitude())));
        }
    }
}