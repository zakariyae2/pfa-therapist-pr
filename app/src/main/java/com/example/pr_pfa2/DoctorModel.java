package com.example.pr_pfa2;

public class DoctorModel {

    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String qualifications;
    private float rating;

    public DoctorModel() {
        // empty constructor required for Firestore deserialization
    }

    public DoctorModel(String fullName, String email, String phoneNumber, String address, String qualifications, float rating) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.qualifications = qualifications;
        this.rating = rating;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
