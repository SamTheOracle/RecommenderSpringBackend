package com.app.recommender.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

public class PhysicalActivityRecord {

    @Id
    private String id;

    private String userId, physicalActivityId,dietId;

    private double burntCalories;

    private LocalDateTime sessionTimeStart,sessionTimeEnd;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPhysicalActivityId() {
        return physicalActivityId;
    }

    public void setPhysicalActivityId(String physicalActivityId) {
        this.physicalActivityId = physicalActivityId;
    }

    public double getBurntCalories() {
        return burntCalories;
    }

    public void setBurntCalories(double burntCalories) {
        this.burntCalories = burntCalories;
    }

    public LocalDateTime getSessionTimeStart() {
        return sessionTimeStart;
    }

    public void setSessionTimeStart(LocalDateTime sessionTimeStart) {
        this.sessionTimeStart = sessionTimeStart;
    }

    public LocalDateTime getSessionTimeEnd() {
        return sessionTimeEnd;
    }

    public void setSessionTimeEnd(LocalDateTime sessionTimeEnd) {
        this.sessionTimeEnd = sessionTimeEnd;
    }

    public String getDietId() {
        return dietId;
    }

    public void setDietId(String dietId) {
        this.dietId = dietId;
    }
}
