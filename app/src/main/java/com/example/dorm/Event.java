package com.example.dorm;

public class Event {

    private String startDate;
    private String time;
    private String address;
    private String EventName;
    private String phoneNumber;


    public Event() {
    }

    public Event(String startDate, String time, String address, String eventName, String phoneNumber) {
        this.startDate = startDate;
        this.time = time;
        this.address = address;
        EventName = eventName;
        this.phoneNumber = phoneNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
