package com.example.finalyearproject.models;

import java.util.Objects;

public class AccidentsLocationList2 {

    public AccidentsLocationList2(){}

    @Override
    public String toString() {
        return "AccidentsLocationList2{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", completed='" + completed + '\'' +
                ", attended='" + attended + '\'' +
                ", contactname='" + contactname + '\'' +
                ", contactnumber='" + contactnumber + '\'' +
                ", mapurl='" + mapurl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccidentsLocationList2)) return false;
        AccidentsLocationList2 that = (AccidentsLocationList2) o;
        return getId() == that.getId() && Objects.equals(getUser_id(), that.getUser_id()) && Objects.equals(getLongitude(), that.getLongitude()) && Objects.equals(getLatitude(), that.getLatitude()) && Objects.equals(getCompleted(), that.getCompleted()) && Objects.equals(getAttended(), that.getAttended()) && Objects.equals(getContactname(), that.getContactname()) && Objects.equals(getContactnumber(), that.getContactnumber()) && Objects.equals(getMapurl(), that.getMapurl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser_id(), getLongitude(), getLatitude(), getCompleted(), getAttended(), getContactname(), getContactnumber(), getMapurl());
    }

    public int id;
    public String user_id;
    public String longitude;
    public String latitude;

    public String completed;
    public String attended;

    public int getId() {
        return id;
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

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public String getMapurl() {
        return mapurl;
    }

    public void setMapurl(String mapurl) {
        this.mapurl = mapurl;
    }

    public String contactname;

    public AccidentsLocationList2(int id, String user_id, String longitude, String latitude, String completed, String attended, String contactname, String contactnumber, String mapurl) {
        this.id = id;
        this.user_id = user_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.completed = completed;
        this.attended = attended;
        this.contactname = contactname;
        this.contactnumber = contactnumber;
        this.mapurl = mapurl;
    }

    public String contactnumber;
    public String mapurl;


}
