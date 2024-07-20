package com.example.finalyearproject.models;

import java.util.Objects;

public class AccidentsLocationList {


    public int id;
    public String user_id;

    public String longitude;

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccidentsLocationList that = (AccidentsLocationList) o;
        return id == that.id && Objects.equals(user_id, that.user_id) && Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, latitude, longitude);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String latitude;

    public AccidentsLocationList(int id, String user_id, String latitude, String longitude) {
        this.id = id;
        this.user_id = user_id;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
