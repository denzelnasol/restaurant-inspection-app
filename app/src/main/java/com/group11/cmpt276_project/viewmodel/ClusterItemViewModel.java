package com.group11.cmpt276_project.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.model.ClusterItem;
import com.group11.cmpt276_project.service.model.InspectionReport;
import com.group11.cmpt276_project.service.model.Restaurant;
import com.group11.cmpt276_project.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClusterItemViewModel {
    private List<ClusterItem> clusterItems;
    private RestaurantsViewModel restaurantsViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;

    BitmapDescriptor happyBitMap;
    BitmapDescriptor sadBitMap;
    BitmapDescriptor neutralBitMap;

    private ClusterItemViewModel() {
        restaurantsViewModel = RestaurantsViewModel.getInstance();
        inspectionReportsViewModel = InspectionReportsViewModel.getInstance();
        clusterItems = new ArrayList<>();
    }

    private static class ClusterItemViewModelHolder {
        private static ClusterItemViewModel INSTANCE = new ClusterItemViewModel();
    }

    public static ClusterItemViewModel getInstance() {
        return ClusterItemViewModelHolder.INSTANCE;
    }

    //public void init(Context context, Map<String, Restaurant> restaurants, Map<String, List<InspectionReport>> inspections)
    public void init(Context context) {
/*        happyBitMap = BitmapDescriptorFactory.fromBitmap(setIconAndSize(R.drawable.happy, context));
        sadBitMap = BitmapDescriptorFactory.fromBitmap(setIconAndSize(R.drawable.sad, context));
        neutralBitMap = BitmapDescriptorFactory.fromBitmap(setIconAndSize(R.drawable.neutral, context));*/
        for (Map.Entry<String, Restaurant> entry : this.restaurantsViewModel.get().getValue().entrySet()) {
            String name = entry.getValue().getName();
            String address = entry.getValue().getPhysicalAddress();
            String hazardRating = setHazardRating(entry);

            double latitude = entry.getValue().getLatitude();
            double longitude = entry.getValue().getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            MarkerOptions markerOptions;
            BitmapDescriptor icon = setIcon(entry);

            markerOptions = new MarkerOptions().position(latLng).icon(icon).snippet(address + " - Hazardous Rating: " + hazardRating).title(name);
            ClusterItem clusterItem = new ClusterItem(markerOptions);
            this.clusterItems.add(clusterItem);
        }

    }

    public List<ClusterItem> get() {
        return this.clusterItems;
    }

    private Bitmap setIconAndSize(int drawable, Context context) {
        int height = 100;
        int width = 100;

        Bitmap b = BitmapFactory.decodeResource(context.getResources(), drawable);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        return smallMarker;
    }

    private BitmapDescriptor setIcon(Map.Entry<String, Restaurant> entry) {
        BitmapDescriptor icon;
        String trackingNumber = this.restaurantsViewModel.getByTrackingNumber(entry.getValue().getTrackingNumber()).getTrackingNumber();
        InspectionReport inspectionReport = this.inspectionReportsViewModel.getMostRecentReport(trackingNumber);

        if (inspectionReport != null && inspectionReportsViewModel.getReports(entry.getValue().getTrackingNumber()).get(0).getHazardRating().equals(Constants.MODERATE)) {
            icon = neutralBitMap;
        }
        else if (inspectionReport != null && inspectionReportsViewModel.getReports(entry.getValue().getTrackingNumber()).get(0).getHazardRating().equals(Constants.CRITICAL)){
            icon = sadBitMap;
        }
        else {
            icon = happyBitMap;
        }

        return icon;
    }

    private String setHazardRating(Map.Entry<String, Restaurant> entry) {
        String hazardRating;
        String trackingNumber = this.restaurantsViewModel.getByTrackingNumber(entry.getValue().getTrackingNumber()).getTrackingNumber();
        InspectionReport inspectionReport = this.inspectionReportsViewModel.getMostRecentReport(trackingNumber);

        if (inspectionReport != null && inspectionReportsViewModel.getReports(entry.getValue().getTrackingNumber()).get(0).getHazardRating().equals(Constants.MODERATE)) {
            hazardRating = "Moderate";
        }
        else if (inspectionReport != null && inspectionReportsViewModel.getReports(entry.getValue().getTrackingNumber()).get(0).getHazardRating().equals(Constants.CRITICAL)){
            hazardRating = "Critical";
        }
        else {
            hazardRating = "Low";
        }

        return hazardRating;
    }
}
