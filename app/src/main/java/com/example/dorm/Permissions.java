package com.example.dorm;

public class Permissions {


    private String startDate;
    private String endDate;
    private String address;
    private String contactName;
    private String relation;
    private String phoneNumber;
    private String studentId;

    public Permissions(String startDate, String endDate, String address, String contactName, String relation, String phoneNumber, String studentId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.contactName = contactName;
        this.relation = relation;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
    }

    public Permissions() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStudentId() { return studentId; }

    public void setStudentId(String studentId) { this.studentId = studentId; }
}
