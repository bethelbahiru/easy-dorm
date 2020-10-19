package com.example.dorm;

public class Food {

    private String course;
    private String appetizer;
    private String mainDish;
    private String dessert;
    private String calory;
    private String date;

    public Food() {
    }

    public Food(String course, String appetizer, String mainDish, String dessert, String calory, String date) {
        this.course = course;
        this.appetizer = appetizer;
        this.mainDish = mainDish;
        this.dessert = dessert;
        this.calory = calory;
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAppetizer() {
        return appetizer;
    }

    public void setAppetizer(String appetizer) {
        this.appetizer = appetizer;
    }

    public String getMainDish() {
        return mainDish;
    }

    public void setMainDish(String mainDish) {
        this.mainDish = mainDish;
    }

    public String getDessert() {
        return dessert;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    public String getCalory() {
        return calory;
    }

    public void setCalory(String calory) {
        this.calory = calory;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
