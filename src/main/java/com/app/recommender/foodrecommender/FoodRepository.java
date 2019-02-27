package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Repository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Repository("foodrepository")
public class FoodRepository implements IFoodRepository {
    private static String foodUri = "http://temphost/ontologies/food#";
    private static Model model = ModelFactory.createDefaultModel();

    public FoodRepository() {
        model.setNsPrefix(FoodRDF.NSPrefix, foodUri);
    }

    @Override
    public void createRdfFood(Food f) {

        String foodName = f.getName().replaceAll("\\s","");
        Resource foodRdf = model.createResource(foodUri + foodName);
        foodRdf.addProperty(FoodRDF.caloriesPer100,f.getCalories().toString());
        foodRdf.addProperty(FoodRDF.carbs,f.getCarbs().toString());
        foodRdf.addProperty(FoodRDF.fat,f.getFat().toString());
        foodRdf.addProperty(FoodRDF.proteins,f.getProteins().toString());
        try(OutputStream out = new FileOutputStream("food.rdf")) {
            model.write(out, "RDF/XML-ABBREV");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
