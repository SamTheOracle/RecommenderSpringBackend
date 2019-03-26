package com.app.recommender.Model;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;


public class FoodRdf {

    public static final String foodUri = "http://temphost/ontologies/food#";
    public static final String NSPrefix = "food";
    private static final Model model = ModelFactory.createDefaultModel();
    public static final Property caloriesPer100Rdf = model.createProperty(foodUri, "caloriesPer100");
    public static final Property fatsPer100Rdf = model.createProperty(foodUri, "fatsPer100");
    public static final Property carbsPer100Rdf = model.createProperty(foodUri, "carbsPer100");
    public static final Property proteinsPer100Rdf = model.createProperty(foodUri, "proteinsPer100");
    public static final Property saltsPer100Rdf = model.createProperty(foodUri, "saltsPer100");
    public static final Property vitaminsPer100Rdf = model.createProperty(foodUri, "vitaminsPer100");

    public static final Property isGoodWithRdf = model.createProperty(foodUri, "isGoodWith");
    public static final Property goodSynergyWithRdf = model.createProperty(foodUri, "goodSynergyWith");
    public static final Property bestEatenAtRdf = model.createProperty(foodUri, "bestEatenAt");
    public static final Property descriptionRdf = model.createProperty(foodUri, "description");

    public static final Property nameRdf = model.createProperty(foodUri, "name");
    public static final Property imageUrlRdf = model.createProperty(foodUri, "imageUrl");
    public static final Property timeStampRdf = model.createProperty(foodUri, "timeStamp");
    public static final Property typeRdf = model.createProperty(foodUri, "type");

    public static final Property idRdf = model.createProperty(foodUri, "id");
    private String name, description, type, imageUrl, rdfOutput, id;
    private Number proteinsPer100, fatsPer100, carbsPer100, vitaminsPer100, saltsPer100, caloriesPer100, timeStamp;
    private String[] goodWith, goodSinergyWith, bestEatenAt;

    public FoodRdf() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Number timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRdfOutput() {
        return rdfOutput;
    }

    public void setRdfOutput(String rdfOutput) {
        this.rdfOutput = rdfOutput;
    }

    public Number getProteinsPer100() {
        return proteinsPer100;
    }

    public void setProteinsPer100(Number proteinsPer100) {
        this.proteinsPer100 = proteinsPer100;
    }

    public Number getFatsPer100() {
        return fatsPer100;
    }

    public void setFatsPer100(Number fatsPer100) {
        this.fatsPer100 = fatsPer100;
    }

    public Number getCarbsPer100() {
        return carbsPer100;
    }

    public void setCarbsPer100(Number carbsPer100) {
        this.carbsPer100 = carbsPer100;
    }

    public Number getVitaminsPer100() {
        return vitaminsPer100;
    }

    public void setVitaminsPer100(Number vitaminsPer100) {
        this.vitaminsPer100 = vitaminsPer100;
    }

    public Number getSaltsPer100() {
        return saltsPer100;
    }

    public void setSaltsPer100(Number saltsPer100) {
        this.saltsPer100 = saltsPer100;
    }

    public Number getCaloriesPer100() {
        return caloriesPer100;
    }

    public void setCaloriesPer100(Number caloriesPer100) {
        this.caloriesPer100 = caloriesPer100;
    }

    public String[] getGoodWith() {
        return goodWith;
    }

    public void setGoodWith(String[] goodWith) {
        this.goodWith = goodWith;
    }

    public String[] getGoodSinergyWith() {
        return goodSinergyWith;
    }

    public void setGoodSinergyWith(String[] goodSinergyWith) {
        this.goodSinergyWith = goodSinergyWith;
    }

    public String[] getBestEatenAt() {
        return bestEatenAt;
    }

    public void setBestEatenAt(String[] bestEatenAt) {
        this.bestEatenAt = bestEatenAt;
    }
}
