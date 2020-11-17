package com.group11.cmpt276_project.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.MapsInitializer;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClusterItemViewModel {
    private Map<String, ClusterItem> clusterItems;
    private Map<LatLng, Restaurant> latLngRestaurantMap;

    private RestaurantsViewModel restaurantsViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;

    private Map<String, BitmapDescriptor> bitmapDescriptorMap;
    private BitmapDescriptor happyBitMap;
    private BitmapDescriptor sadBitMap;
    private BitmapDescriptor neutralBitMap;


    private ClusterItemViewModel() {
    }

    private static class ClusterItemViewModelHolder {
        private static ClusterItemViewModel INSTANCE = new ClusterItemViewModel();
    }

    public static ClusterItemViewModel getInstance() {
        return ClusterItemViewModelHolder.INSTANCE;
    }

    public void init(Context context, RestaurantsViewModel restaurantsViewModel, InspectionReportsViewModel inspectionReportsViewModel) {

        MapsInitializer.initialize(context);

        if(this.clusterItems != null) {
            return;
        }

        this.clusterItems = new HashMap<>();
        this.latLngRestaurantMap = new HashMap<>();

        this.inspectionReportsViewModel = inspectionReportsViewModel;
        this.restaurantsViewModel = restaurantsViewModel;

        this.happyBitMap = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.happy, context));
        this.sadBitMap = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.sad, context));
        this.neutralBitMap = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.neutral, context));

        this.bitmapDescriptorMap = new HashMap<String, BitmapDescriptor>(){{
            put(Constants.HIGH, sadBitMap);
            put(Constants.LOW, happyBitMap);
            put(Constants.MODERATE, neutralBitMap);
        }};

        for (Map.Entry<String, Restaurant> entry : this.restaurantsViewModel.get().getValue().entrySet()) {

            Restaurant restaurant = entry.getValue();

            String trackingNumber = restaurant.getTrackingNumber();
            String name = restaurant.getName();
            String address = restaurant.getPhysicalAddress();
            String hazardRating = getHazardRating(trackingNumber);

            double latitude = restaurant.getLatitude();
            double longitude = restaurant.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            BitmapDescriptor icon = getIcon(trackingNumber);

            MarkerOptions  markerOptions = new MarkerOptions().position(latLng).icon(icon).snippet(context.getString(R.string.hazard_text, address, hazardRating)).title(name);
            ClusterItem clusterItem = new ClusterItem(markerOptions);
            this.clusterItems.put(trackingNumber, clusterItem);
            this.latLngRestaurantMap.put(latLng, restaurant);
        }

    }

    public Map<String, ClusterItem> get() {
        return this.clusterItems;
    }

    public Restaurant getRestaurantFromCoords(LatLng latLng) {
        return latLngRestaurantMap.get(latLng);
    }

    private Bitmap getBitmap(int drawable, Context context) {
        int height = 100;
        int width = 100;

        Bitmap b = BitmapFactory.decodeResource(context.getResources(), drawable);
        Bitmap bitMap = Bitmap.createScaledBitmap(b, width, height, false);

        return bitMap;
    }

    private BitmapDescriptor getIcon(String trackingNumber) {

        InspectionReport mostRecent = this.inspectionReportsViewModel.getMostRecentReport(trackingNumber);

        String hazardRating = "";

        if (mostRecent != null) {
            hazardRating = mostRecent.getHazardRating();
        }

        return this.bitmapDescriptorMap.getOrDefault(hazardRating, happyBitMap);
    }

    private String getHazardRating(String trackingNumber) {
        String hazardRating = "";

        InspectionReport mostRecent =  inspectionReportsViewModel.getMostRecentReport(trackingNumber);

        if (mostRecent != null) {
            hazardRating = mostRecent.getHazardRating();
        }

        return hazardRating;
    }
}
