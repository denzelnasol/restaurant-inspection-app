package com.group11.cmpt276_project.service.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GPSCoordiantes implements Parcelable {

    double latitude;
    double longitude;

    public GPSCoordiantes(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected GPSCoordiantes(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
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
}
