package com.group11.cmpt276_project.viewmodel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.group11.cmpt276_project.R;
import com.group11.cmpt276_project.service.model.ClusterItem;

public class ClusterRenderer extends DefaultClusterRenderer<ClusterItem> {


    public ClusterRenderer(Context context, GoogleMap map, ClusterManager<ClusterItem> clusterManager) {
        super(context, map, clusterManager);
        clusterManager.setRenderer(this);
    }


    @Override
    protected void onBeforeClusterItemRendered(ClusterItem clusterItem, MarkerOptions markerOptions) {
        if (clusterItem.getIcon() != null) {
            markerOptions.icon(clusterItem.getIcon()); //Here you retrieve BitmapDescriptor from ClusterItem and set it as marker icon
        }
        markerOptions.visible(true);
    }

//    private Bitmap changeMarker(int drawable) {
//        int height = 100;
//        int width = 100;
//
//        Bitmap b = BitmapFactory.decodeResource(context.getResources(), drawable);
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
//        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
//
//        return smallMarker;
//    }
}