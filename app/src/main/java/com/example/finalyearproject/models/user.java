package com.example.finalyearproject.models;

import java.util.Objects;

public class user {

    public int id;
    public String email;
    public String emergency_contact;
    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", emergency_contact='" + emergency_contact + '\'' +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        user user = (user) o;
        return id == user.id && Objects.equals(email, user.email) && Objects.equals(emergency_contact, user.emergency_contact);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, emergency_contact);
    }

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

    public user(int id, String email, String emergency_contact) {
        this.id = id;
        this.email = email;
        this.emergency_contact = emergency_contact;
    }

}
