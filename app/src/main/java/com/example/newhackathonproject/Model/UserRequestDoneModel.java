package com.example.newhackathonproject.Model;

public class UserRequestDoneModel {


    String Address;
    String ContactNo;
    String UserMail;
    String username;
    String PeopleCount;
    long timestamp;
    String AcceptedBy;

    public UserRequestDoneModel(String address, String contactNo, String userMail, String username, String peopleCount, long timestamp, String acceptedBy) {
        Address = address;
        ContactNo = contactNo;
        UserMail = userMail;
        this.username = username;
        PeopleCount = peopleCount;
        this.timestamp = timestamp;
        AcceptedBy = acceptedBy;
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

    public String getAcceptedBy() {
        return AcceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        AcceptedBy = acceptedBy;
    }
}
