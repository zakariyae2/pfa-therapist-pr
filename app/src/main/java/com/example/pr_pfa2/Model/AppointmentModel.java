package com.example.pr_pfa2.Model;

public class AppointmentModel {
    private String fullName;
    private String emaildoc;
    private String emailpat;
    private String phone;
    private String userID;
    private String date;
    private String hour;
    private String month;
    private String state;

    public AppointmentModel() {
        // empty constructor required for Firestore deserialization
    }

    public AppointmentModel(String fullName, String emaildoc, String emailpat, String phone,String userID, String state, String hour) {
        this.fullName = fullName;
        this.emaildoc = emaildoc;
        this.emailpat = emailpat;
        this.phone = phone;
        this.userID = userID;
        this.state = state;
        this.hour = hour;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmaildoc() {
        return emaildoc;
    }

    public void setEmaildoc(String emaildoc) {
        this.emaildoc = emaildoc;
    }

    public String getEmailpat() {
        return emailpat;
    }

    public void setEmailpat(String emailpat) {
        this.emailpat = emailpat;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}