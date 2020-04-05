package com.example.newhackathonproject.Model;

import com.google.firebase.database.ServerValue;

import java.util.Date;

public class RequestModel {

    String Address;
    String ContactNo;
    String UserMail;
    String username;
    String PeopleCount;
    long timestamp;


    public RequestModel(String address, String contactNo, String userMail, String username, String peopleCount, long timestamp) {
        Address = address;
        ContactNo = contactNo;
        UserMail = userMail;
        this.username = username;
        PeopleCount = peopleCount;
        this.timestamp = timestamp;
    }


    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getUserMail() {
        return UserMail;
    }

    public void setUserMail(String userMail) {
        UserMail = userMail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPeopleCount() {
        return PeopleCount;
    }

    public void setPeopleCount(String peopleCount) {
        PeopleCount = peopleCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
