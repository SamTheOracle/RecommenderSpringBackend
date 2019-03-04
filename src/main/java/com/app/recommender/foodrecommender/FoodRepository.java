package com.app.recommender.foodrecommender;

import com.app.recommender.Model.Food;
import com.app.recommender.Model.FoodRdfNotFoundException;
import org.apache.jena.rdf.model.*;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository("foodrepository")
public class FoodRepository implements IFoodRecommenderRepository {
    private static String foodUri = "http://temphost/ontologies/food#";
    private static Model model = ModelFactory.createDefaultModel();

    public FoodRepository() {
        model.setNsPrefix(FoodRDF.NSPrefix, foodUri);
    }

    @Override
    public void createRdfFood(Food f) throws FileNotFoundException {

        File file = new File("food.rdf");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            model.read(reader, null);
        }


        String foodName = f.getName().replaceAll("\\s", "");
        Resource foodRdf = model.createResource(foodUri + foodName);
//        Resource pineapple = model.createResource(foodUri + "Pineapple");
        foodRdf.addProperty(FoodRDF.caloriesPer100, f.getCalories().toString());
        foodRdf.addProperty(FoodRDF.carbs, f.getCarbs().toString());
        foodRdf.addProperty(FoodRDF.fat, f.getFat().toString());
        foodRdf.addProperty(FoodRDF.proteins, f.getProteins().toString());
        foodRdf.addProperty(FoodRDF.name, f.getName());

//        for (String foodNameGoodWith : f.getGoodWith()) {
//            ResIterator rsIterator = model.listSubjectsWithProperty(FoodRDF.name, foodNameGoodWith);
//            if (rsIterator.hasNext()) {
//                Resource r = rsIterator.nextResource();
//                Statement statement = ResourceFactory.createStatement(foodRdf, FoodRDF.isGoodWith, r);
//                model.add(statement);
//                while (rsIterator.hasNext()) {
//                    Resource res = rsIterator.nextResource();
//                    Statement st = ResourceFactory.createStatement(foodRdf, FoodRDF.isGoodWith, res);
//                    model.add(st);
//                }
//            } else {
//                Statement s = ResourceFactory.createStatement(foodRdf, FoodRDF.isGoodWith, foodNameGoodWith)
//            }
//
//        }
        try (OutputStream out = new FileOutputStream("food.rdf")) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void addStatementGoodWith(String subjectName, String foodNameGoodWith) throws FoodRdfNotFoundException {
        File file = new File("food.rdf");
        if (file.exists()) {
            FileReader reader = null;
            try {
                reader = new FileReader(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            model.read(reader, null);
            Resource resourceSubject = model.getResource(FoodRDF.foodUri + subjectName);
            Resource resourceObject = model.getResource(FoodRDF.foodUri + foodNameGoodWith);
            if (resourceSubject == null || resourceObject == null) {
                throw new FoodRdfNotFoundException("Error, foodRdf: " + foodNameGoodWith + " not found!");
            }
            model.add(ResourceFactory.createStatement(resourceSubject, FoodRDF.isGoodWith, resourceObject));


        }
        try (OutputStream out = new FileOutputStream("food.rdf")) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Resource> getRdfFoodForRecommendation(String name) {
        List<Resource> recommendedFoods = new ArrayList<>();

        Model modelToQuery = ModelFactory.createDefaultModel();
        try {
            File file = new File("food.rdf");
            FileReader reader = new FileReader(file);
            modelToQuery.read(reader, null);
            Resource r = modelToQuery.getResource(FoodRDF.foodUri + name);
            StmtIterator stmtIterator = modelToQuery.listStatements(null, FoodRDF.isGoodWith, r);

            while (stmtIterator.hasNext()) {
                Statement s = stmtIterator.nextStatement();
                recommendedFoods.add(s.getSubject());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return recommendedFoods;
    }
}
