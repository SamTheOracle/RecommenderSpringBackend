package com.app.recommender.Model;

import java.time.LocalDate;
import java.util.Comparator;

public class DietHistory implements Comparator<DietHistory> {
    private String timeStamp;
    private String name;
    private double totalCalories;


    public DietHistory(String timeStamp, String name, double totalCalories) {
        this.timeStamp = timeStamp;
        this.name = name;
        this.totalCalories = totalCalories;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compare(DietHistory o1, DietHistory o2) {
        LocalDate o1Date = LocalDate.parse(o1.getTimeStamp());
        LocalDate o2Date = LocalDate.parse(o2.getTimeStamp());
        return o1Date.compareTo(o2Date);
    }
}
