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

import java.util.HashMap;
import java.util.Map;

//Singleton that holds a map of all restaurant markers and gives each one a unique coordinate
public class ClusterItemViewModel {
    private Map<String, ClusterItem> clusterItems;

    private RestaurantsViewModel restaurantsViewModel;
    private InspectionReportsViewModel inspectionReportsViewModel;

    private Map<String, BitmapDescriptor> bitmapDescriptorMap;
    private BitmapDescriptor happyBitMap;
    private BitmapDescriptor sadBitMap;
    private BitmapDescriptor neutralBitMap;

    private final double DEFAULT_COORDINATE_OFFSET = 0.00002;


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

        HashMap<LatLng, Double> existingCoords = new HashMap<>();

        if(this.clusterItems != null) {
            return;
        }

        this.clusterItems = new HashMap<>();

        this.inspectionReportsViewModel = inspectionReportsViewModel;
        this.restaurantsViewModel = restaurantsViewModel;

        this.happyBitMap = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.low_hazard_marker, context));
        this.sadBitMap = BitmapDescriptorFactory.fromBitmap(getBitmap(R.drawable.high_hazard_marker, context));
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

            if (existingCoords.containsKey(latLng)) {
                double offset = existingCoords.get(latLng);
                latLng = new LatLng(latLng.latitude + 0.00001, latLng.longitude + 0.00001);
                existingCoords.put(latLng, offset + 0.00001);
            }
            else {
                existingCoords.put(latLng, DEFAULT_COORDINATE_OFFSET);
            }
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).icon(icon).snippet(context.getString(R.string.hazard_text, address, hazardRating)).title(name);
            ClusterItem clusterItem = new ClusterItem(markerOptions, trackingNumber);
            this.clusterItems.put(trackingNumber, clusterItem);
        }

    }

    public Map<String, ClusterItem> get() {
        return this.clusterItems;
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
