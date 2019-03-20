package com.app.recommender.foodrecommender;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;


public class FoodRdf {

    private static final Model model = ModelFactory.createDefaultModel();


    public static final String foodUri = "http://temphost/ontologies/food#";
    public static final String NSPrefix = "food";

    public static final Property caloriesPer100Rdf = model.createProperty(foodUri, "caloriesPer100");
    public static final Property fatsRdf = model.createProperty(foodUri, "fats");
    public static final Property carbsRdf = model.createProperty(foodUri, "carbs");
    public static final Property proteinsRdf = model.createProperty(foodUri, "proteins");
    public static final Property saltsRdf = model.createProperty(foodUri, "salts");
    public static final Property vitaminsRdf = model.createProperty(foodUri, "vitamins");

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
    private Number proteins, fats, carbs, vitamins, salts, caloriesPer100, timeStamp;
    private String[] goodWith, goodSinergyWith, bestEatenAt;

    public FoodRdf() {
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
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

    public Number getProteins() {
        return proteins;
    }

    public void setProteins(Number proteins) {
        this.proteins = proteins;
    }

    public Number getFats() {
        return fats;
    }

    public void setFats(Number fats) {
        this.fats = fats;
    }

    public Number getCarbs() {
        return carbs;
    }

    public void setCarbs(Number carbs) {
        this.carbs = carbs;
    }

    public Number getVitamins() {
        return vitamins;
    }

    public void setVitamins(Number vitamins) {
        this.vitamins = vitamins;
    }

    public Number getSalts() {
        return salts;
    }

    public void setSalts(Number salts) {
        this.salts = salts;
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
