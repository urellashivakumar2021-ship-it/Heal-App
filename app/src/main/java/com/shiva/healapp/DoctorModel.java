package com.shiva.healapp;

public class DoctorModel {

    private int id;
    private String name;
    private String email;
    private String specialization;
    private String phone;

    public DoctorModel(int id, String name, String email, String specialization, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.specialization = specialization;
        this.phone = phone;
    }

    // ==== GETTERS ====

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    // ==== OPTIONAL SETTERS IF YOU NEED ====

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
