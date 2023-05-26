package com.example.pr_pfa2.Model;

public class UserModel {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String id;
    private float rating;


    public UserModel() {
        // empty constructor required for Firestore deserialization
    }

    public UserModel(String fullName, String email, String phoneNumber, String address, float rating, String city, String id) {
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.rating = rating;
        this.city = city;
        this.id = id;
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


    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCity() {return city;}

    public void setCity(String city) {this.city = city;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}


