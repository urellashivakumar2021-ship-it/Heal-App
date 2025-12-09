package com.shiva.healapp;

public class UserLogModel {
    String timestamp, diet, activity, mood;
    int sleep, stress, heart;
    double water;

    public UserLogModel(String timestamp, int sleep, double water, int stress,
                        String diet, String activity, String mood, int heart) {
        this.timestamp = timestamp;
        this.sleep = sleep;
        this.water = water;
        this.stress = stress;
        this.diet = diet;
        this.activity = activity;
        this.mood = mood;
        this.heart = heart;
    }
}
