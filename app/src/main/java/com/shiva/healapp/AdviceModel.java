package com.shiva.healapp;

public class AdviceModel {
    private final int id;
    private final int userId;
    private final int doctorId;
    private final String adviceText;
    private final String timestamp;

    public AdviceModel(int id, int userId, int doctorId, String adviceText, String timestamp) {
        this.id = id;
        this.userId = userId;
        this.doctorId = doctorId;
        this.adviceText = adviceText;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getDoctorId() { return doctorId; }
    public String getAdviceText() { return adviceText; }
    public String getTimestamp() { return timestamp; }
}
