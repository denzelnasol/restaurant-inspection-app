package com.group11.cmpt276_project.service.model;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

// A cluster item model that represents a restaurant marker
public class ClusterItem implements com.google.maps.android.clustering.ClusterItem {
    private LatLng position;
    private String title;
    private String snippet;
    private BitmapDescriptor icon;

    public ClusterItem() {

    }

    public ClusterItem(MarkerOptions markerOptions) {
        position = markerOptions.getPosition();
        this.title = markerOptions.getTitle();
        this.snippet = markerOptions.getSnippet();
        this.icon = markerOptions.getIcon();
    }

    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public BitmapDescriptor getIcon() {
        return icon;
    }

    public void setIcon(BitmapDescriptor icon) {
        this.icon = icon;
    }
}
