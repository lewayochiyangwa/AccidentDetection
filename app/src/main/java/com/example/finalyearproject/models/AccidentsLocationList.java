package com.example.finalyearproject.models;

import java.util.Objects;

public class AccidentsLocationList {

    public AccidentsLocationList(){}

    public int id;
    public String user_id;

    public String longitude;
    public String latitude;

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccidentsLocationList that = (AccidentsLocationList) o;
        return id == that.id && Objects.equals(user_id, that.user_id) && Objects.equals(longitude, that.longitude) && Objects.equals(latitude, that.latitude) && Objects.equals(completed, that.completed) && Objects.equals(attended, that.attended);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user_id, longitude, latitude, completed, attended);
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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getAttended() {
        return attended;
    }

    public void setAttended(String attended) {
        this.attended = attended;
    }

    public AccidentsLocationList(int id, String user_id, String longitude, String latitude, String completed, String attended) {
        this.id = id;
        this.user_id = user_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.completed = completed;
        this.attended = attended;
    }

    public String completed;
    public String attended;


}
