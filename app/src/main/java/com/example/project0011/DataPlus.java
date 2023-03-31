package com.example.project0011;

public class DataPlus extends Data{
    private String date;


    public DataPlus()
    {}

    public DataPlus(String date, Data data) {
        this.date = date;
        this.steps = data.steps;
        this.distance = data.distance;
        this.calorie = data.calorie;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
