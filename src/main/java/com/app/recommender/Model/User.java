package com.app.recommender.Model;

import org.joda.time.LocalDate;
import org.joda.time.Years;
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

    private double basicMetabolicRate;


    public User() {
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

    public double getBasicMetabolicRate() {
        return basicMetabolicRate;
    }

    public void setBasicMetabolicRate(double basicMetabolicRate) {
        this.basicMetabolicRate = basicMetabolicRate;
    }

    public void computeBMR() {
        LocalDate now = LocalDate.now();
        int age = Years.yearsBetween(LocalDate.fromDateFields(this.birthDate), now).getYears();
        switch (this.gender) {
            case "Male":
                this.basicMetabolicRate = 10 * this.weight + 6.25 * this.height - 5 * age + 5;
                break;
            case "Female":
                this.basicMetabolicRate = 10 * this.weight + 6.25 * this.height - 5 * age - 161;
                break;
        }
    }
}
