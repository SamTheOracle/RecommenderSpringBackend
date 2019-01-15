package com.app.recommender.Model;

import java.util.Date;

public class Cycling implements PhysicalActivity {



    private static String TYPE="Cycling";

    private Date startDate;

    private Date endDate;

    private double adherence;

    private double currentValue;

    private String unitOfMeasure;

    private double total;

    public Cycling(Date startDate, Date endDate, double adherence, double currentValue, String unitOfMeasure, double total) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.adherence = adherence;
        this.currentValue = currentValue;
        this.unitOfMeasure = unitOfMeasure;
        this.total = total;
    }

    public Cycling(){}

    public static String getTYPE() {
        return TYPE;
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getAdherence() {
        return adherence;
    }

    public void setAdherence(double adherence) {
        this.adherence = adherence;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    @Override
    public double computeAdherence() {
        return currentValue / total * 100;
    }
}
