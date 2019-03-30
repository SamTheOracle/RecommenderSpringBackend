package com.app.recommender.Model;

import org.springframework.data.annotation.Id;

public class Nutritionist {
    @Id
    private String id;

    private String email, password, userName;

    private String[] patientsIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String[] getPatientsIds() {
        return patientsIds;
    }

    public void setPatientsIds(String[] patientsIds) {
        this.patientsIds = patientsIds;
    }
}
