package com.shiva.healapp;

public class PatientModel {

    int id;
    String name;
    String email;
    String lastLog;

    public PatientModel(int id, String name, String email, String lastLog) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.lastLog = lastLog;
    }
}
