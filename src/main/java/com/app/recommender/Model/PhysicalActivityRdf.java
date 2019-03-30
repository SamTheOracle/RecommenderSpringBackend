package com.app.recommender.Model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

import java.time.LocalDate;


public class PhysicalActivityRdf {

    public static final String physicalActivityUri = "http://temphost/ontologies/physicalActivity#";
    public static final String NSPrefix = "phyact";

    private static final Model model = ModelFactory.createDefaultModel();

    public static final Property idRdf = model.createProperty(physicalActivityUri, "id");

    public static final Property nameRdf = model.createProperty(physicalActivityUri, "name");

    public static final Property userIdRdf = model.createProperty(physicalActivityUri, "userId");

    public static final Property startDateRdf = model.createProperty(physicalActivityUri, "startDate");

    public static final Property endDateRdf = model.createProperty(physicalActivityUri, "endDate");

    public static final Property caloriesPerHourRdf = model.createProperty(physicalActivityUri, "caloriesPerHour");

    public static final Property descriptionRdf = model.createProperty(physicalActivityUri, "description");

    public static final Property imageUrlRdf = model.createProperty(physicalActivityUri, "imageUrl");


    private String userId, name, id, rdfOutput, description, imageUrl;

    private LocalDate startDate, endDate;

    private double caloriesPerHour;


    public PhysicalActivityRdf() {

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRdfOutput() {
        return rdfOutput;
    }

    public void setRdfOutput(String rdfOutput) {
        this.rdfOutput = rdfOutput;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getCaloriesPerHour() {
        return caloriesPerHour;
    }

    public void setCaloriesPerHour(double caloriesPerHour) {
        this.caloriesPerHour = caloriesPerHour;
    }
}
