package com.example.dorm;

public class Payment {

    private String name;
    private String surname;
    private String month;
    private String paymentMethod;

    public Payment() {
    }

    public Payment(String name, String surname, String month, String paymentMethod) {
        this.name = name;
        this.surname = surname;
        this.month = month;
        this.paymentMethod = paymentMethod;
    }

    public String getFullName() {
        return name;
    }

    public void setFullName(String fullName) {
        this.name = fullName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
