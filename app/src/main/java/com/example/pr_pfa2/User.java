package com.example.pr_pfa2;

public class User{
    private String Firstname;
    private String Lastname;
    private int Phone;
    private String Email;

    public User(String firstname, String lastname, String phone, String email) {
        Firstname = firstname;
        Lastname = lastname;
        Phone = Integer.parseInt(phone);
        Email = email;
    }

    public User() {

    }

    public User(String fname, String lname, int phoneint, String emailaddr) {
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}

