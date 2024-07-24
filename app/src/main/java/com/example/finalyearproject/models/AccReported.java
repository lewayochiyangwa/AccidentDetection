package com.example.finalyearproject.models;

import java.util.Objects;

public class AccReported {
    public AccReported(String name, String phone, String message, String report_time) {
        this.name = name;
        this.phone = phone;
        this.message = message;
        this.report_time = report_time;
    }
    public AccReported(){

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccReported that = (AccReported) o;
        return Objects.equals(name, that.name) && Objects.equals(phone, that.phone) && Objects.equals(message, that.message) && Objects.equals(report_time, that.report_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, message, report_time);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReport_time() {
        return report_time;
    }

    public void setReport_time(String report_time) {
        this.report_time = report_time;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String name, phone, message, report_time,severity;
}
