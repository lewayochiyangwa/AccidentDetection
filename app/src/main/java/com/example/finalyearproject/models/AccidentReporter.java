package com.example.finalyearproject.models;

public class AccidentReporter {

    public AccidentReporter(int reporter_id, String location, String severity, String photo) {
        this.reporter_id = reporter_id;
        this.location = location;
        this.severity = severity;
        this.photo = photo;
    }

    public AccidentReporter() {
    }

    public int reporter_id;
    public String location;

    public int getReporter_id() {
        return reporter_id;
    }

    public void setReporter_id(int reporter_id) {
        this.reporter_id = reporter_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

   public  String severity;
    public String photo;
}
