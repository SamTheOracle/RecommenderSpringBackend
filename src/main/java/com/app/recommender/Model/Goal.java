package com.app.recommender.Model;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Goal {

    private Double weeklyGoal;
    private String dietId, userId, physicalActivityId;
    private double adherence;
    @Id
    private String id;

    public double computeAdherence(List<PhysicalActivityRecord> records) {
        double totalCaloriesForDate = records.stream()
                .mapToDouble(PhysicalActivityRecord::getBurntCalories).sum();

        this.adherence = (100 / this.weeklyGoal) * totalCaloriesForDate;
        return adherence;
    }


    public String getPhysicalActivityId() {
        return physicalActivityId;
    }

    public void setPhysicalActivityId(String physicalActivityId) {
        this.physicalActivityId = physicalActivityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getWeeklyGoal() {
        return weeklyGoal;
    }

    public void setWeeklyGoal(Double weeklyGoal) {
        this.weeklyGoal = weeklyGoal;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }

    public double getAdherence() {
        return adherence;
    }

    public void setAdherence(double adherence) {
        this.adherence = adherence;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
