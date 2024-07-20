package com.example.finalyearproject.models;

public class LocationPost {
    public String latitude;
    public String longitude;

    public LocationPost(String latitude, String longitude, String user_id) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.user_id = user_id;
    }

    public LocationPost(){

    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String user_id;
}
