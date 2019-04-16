package com.ferraris.localisermastation.bean;

import com.google.android.gms.maps.model.LatLng;

public class PositionBean {
    private double lng;
    private double lat;


    public LatLng getLatLng() {
        return new LatLng(lat, lng);
    }


    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Class PositionBean [lng = " + lng + ", lat = " + lat + "]";
    }
}
