package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "employee")
@XmlAccessorType(XmlAccessType.NONE)
public class FoodRDF {

    private static final Model model = ModelFactory.createDefaultModel();
    private static Food food;

    public static final String foodUri = "http://temphost/ontologies/food#";
    public static final String NSPrefix = "food";
    public static final Property caloriesPer100 = model.createProperty(foodUri, "caloriesPer100");
    public static final Property fat = model.createProperty(foodUri, "fat");
    public static final Property carbs = model.createProperty(foodUri, "carbs");
    public static final Property proteins = model.createProperty(foodUri, "proteins");
    public static final Property isGoodWith = model.createProperty(foodUri, "isGoodWith");
    public static final Property name = model.createProperty(foodUri, "name");



}
