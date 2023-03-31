package com.example.project0011;

public class Data {
    int steps;
    int calorie;
    float distance;
    String  date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Data(int steps, int calorie, float distance) {
        this.steps = steps;
        this.calorie = calorie;
        this.distance = distance;
    }

    public Data() {
    }

    public int getSteps() {
        return steps;
    }
    public String getSSteps() {return steps+"";}


    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getCalorie() {
        return calorie;
    }
    public String getSCalorie() {
        return calorie+"";
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public float getDistance() {
        return distance;
    }
    public String getSDistance() {
        return distance+"";
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
}
