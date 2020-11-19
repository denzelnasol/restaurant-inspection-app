package com.group11.cmpt276_project.service.model;

import android.os.Parcel;
import android.os.Parcelable;

//Class that represents the gps coordinates that will be used to render the coordinates clicked on in the restaurant detail
public class GPSCoordiantes implements Parcelable {

    private double latitude;
    private double longitude;
    private String trackingNumber;

    public GPSCoordiantes(double latitude, double longitude, String trackingNumber) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.trackingNumber = trackingNumber;
    }

    protected GPSCoordiantes(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.trackingNumber = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.trackingNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GPSCoordiantes> CREATOR = new Creator<GPSCoordiantes>() {
        @Override
        public GPSCoordiantes createFromParcel(Parcel in) {
            return new GPSCoordiantes(in);
        }

        @Override
        public GPSCoordiantes[] newArray(int size) {
            return new GPSCoordiantes[size];
        }
    };

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
