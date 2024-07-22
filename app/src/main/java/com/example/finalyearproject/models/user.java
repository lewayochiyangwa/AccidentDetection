package com.example.finalyearproject.models;

import java.util.Objects;

public class user {

    public int id;
    public String email;
    public String emergency_contact;
    public String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmergency_contact() {
        return emergency_contact;
    }

    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public user(int id, String email, String emergency_contact, String role) {
        this.id = id;
        this.email = email;
        this.emergency_contact = emergency_contact;
        this.role = role;
    }
}
