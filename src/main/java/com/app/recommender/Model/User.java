package com.app.recommender.Model;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class User {

    @Id
    private String id;

    private String userName;

    private String email;

    private String password;

    private Date birthDate;

    private String gender;

    private int weight;

    private int height;

    private PhysicalActivity currentGoal;

    private PhysicalActivity[] olderPhysicalActivities;

    private int BMIndex;


    public User() {
    }

    public User(String id, String userName, String email, String password, Date birthDate,
                String gender, int weight, int height, PhysicalActivity currentGoal, PhysicalActivity[] olderPhysicalActivities) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.height = height;
        this.currentGoal = currentGoal;
        this.olderPhysicalActivities = olderPhysicalActivities;
    }

    public User(String id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PhysicalActivity getCurrentGoal() {
        return currentGoal;
    }

    public void setCurrentGoal(PhysicalActivity currentGoal) {
        this.currentGoal = currentGoal;
    }

    public PhysicalActivity[] getOlderPhysicalActivities() {
        return olderPhysicalActivities;
    }

    public void setOlderPhysicalActivities(PhysicalActivity[] olderPhysicalActivities) {
        this.olderPhysicalActivities = olderPhysicalActivities;
    }

    public int getBMIndex() {
        return BMIndex;
    }

    public void setBMIndex(int BMIndex) {
        this.BMIndex = BMIndex;
    }
}
