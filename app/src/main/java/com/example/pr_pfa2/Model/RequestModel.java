package com.example.pr_pfa2.Model;

public class RequestModel {
    private String fullName;
    private String emaildoc;
    private String emailpat;
    private String status;


    public RequestModel() {
        // empty constructor required for Firestore deserialization
    }

    public RequestModel(String fullName, String emaildoc, String emailpat, String status) {
        this.fullName = fullName;
        this.emaildoc = emaildoc;
        this.emailpat = emailpat;
        this.status = status;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getemaildoc() {
        return emaildoc;
    }

    public void setEmaildoc(String emaildoc) {
        this.emaildoc = emaildoc;
    }

    public String getemailpat() {
        return emailpat;
    }

    public void setEmailpat(String emailpat) {
        this.emailpat = emailpat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

