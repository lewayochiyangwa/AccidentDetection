package com.example.finalyearproject.models;

import java.util.Objects;

public class ProfileUpdate {
    public String name;
    public String  allergies;
    public String medical_insurance;
    public String emergency_contact;
   public  int id;

    public ProfileUpdate(String name, String allergies, String medical_insurance, String emergency_contact, int id) {
        this.name = name;
        this.allergies = allergies;
        this.medical_insurance = medical_insurance;
        this.emergency_contact = emergency_contact;
        this.id = id;
    }

    public ProfileUpdate(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedical_insurance() {
        return medical_insurance;
    }

    public void setMedical_insurance(String medical_insurance) {
        this.medical_insurance = medical_insurance;
    }

    public String getEmergency_contact() {
        return emergency_contact;
    }

    public void setEmergency_contact(String emergency_contact) {
        this.emergency_contact = emergency_contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProfileUpdate{" +
                "name='" + name + '\'' +
                ", allergies='" + allergies + '\'' +
                ", medical_insurance='" + medical_insurance + '\'' +
                ", emergency_contact='" + emergency_contact + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProfileUpdate)) return false;
        ProfileUpdate that = (ProfileUpdate) o;
        return getId() == that.getId() && Objects.equals(getName(), that.getName()) && Objects.equals(getAllergies(), that.getAllergies()) && Objects.equals(getMedical_insurance(), that.getMedical_insurance()) && Objects.equals(getEmergency_contact(), that.getEmergency_contact());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAllergies(), getMedical_insurance(), getEmergency_contact(), getId());
    }
}
