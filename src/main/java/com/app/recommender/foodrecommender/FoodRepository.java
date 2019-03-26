package com.app.recommender.foodrecommender;

import com.app.recommender.Model.FoodRdf;
import org.apache.jena.rdf.model.*;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository("foodrepository")
public class FoodRepository implements IFoodRecommenderRepository {


    public FoodRepository() {
    }


    @Override
    public FoodRdf createRdfFood(FoodRdf foodRDF, String userId) throws FileNotFoundException {
        File file = new File("food" + userId + ".rdf");
        Model model = ModelFactory.createDefaultModel();
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            model.read(reader, null);
        } else {
            try {
                if (file.createNewFile()) {
                    model.setNsPrefix(FoodRdf.NSPrefix, "http://temphost/ontologies/food");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        addResourceToModel(foodRDF, model);


        try (OutputStream out = new FileOutputStream("food" + userId + ".rdf")) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model modelForOutput = ModelFactory.createDefaultModel();
        addResourceToModel(foodRDF, modelForOutput);
        StringWriter writer = new StringWriter();
        modelForOutput.write(writer, "RDF/XML");
        foodRDF.setRdfOutput(writer.toString());
        return foodRDF;
    }


    @Override
    public List<FoodRdf> getRdfFoodForRecommendation(String foodId, String userId) throws FileNotFoundException {
        List<FoodRdf> recommendedFoods = new ArrayList<>();

        Model modelToQuery = ModelFactory.createDefaultModel();

        File file = new File("food" + userId + ".rdf");
        FileReader reader = new FileReader(file);
        modelToQuery.read(reader, null);
        Resource r = null;
        ResIterator resIterator = modelToQuery.listResourcesWithProperty(FoodRdf.idRdf, foodId);
        while (resIterator.hasNext()) {
            r = resIterator.nextResource();
        }
        if (r == null) {
            return null;
        }
        StmtIterator stmtIterator = modelToQuery.listStatements(r, FoodRdf.isGoodWithRdf, (RDFNode) null);

        while (stmtIterator.hasNext()) {
            Statement s = stmtIterator.nextStatement();
            Resource resourceToAdd = modelToQuery.getResource(s.getObject().toString());
            FoodRdf foodRdfToAdd = fromRdfToFood(modelToQuery, resourceToAdd);
            recommendedFoods.add(foodRdfToAdd);
        }


        return recommendedFoods;
    }

    @Override
    public FoodRdf getFoodById(String foodId, String userId) throws IOException {
        Model modelToQuery = ModelFactory.createDefaultModel();

        File file = new File("food" + userId + ".rdf");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            modelToQuery.read(reader, null);
        } else {
            return null;
        }


        Resource r = null;
        ResIterator resIterator = modelToQuery.listResourcesWithProperty(FoodRdf.idRdf, foodId);
        while (resIterator.hasNext()) {
            r = resIterator.nextResource();
        }
        if (r == null) {
            return null;
        }
        FoodRdf foodRdf = fromRdfToFood(modelToQuery, r);
//            addResourceToModel();
//            newTempModel.create
        if (foodRdf == null) {
            return null;
        }
        Model newTempModel = ModelFactory.createDefaultModel();

        addResourceToModel(foodRdf, newTempModel);
        StringWriter writer = new StringWriter();
        newTempModel.write(writer, "RDF/XML");
        foodRdf.setRdfOutput(writer.toString());
        return foodRdf;


    }

    @Override
    public FoodRdf[] getAllFoodFromOntology(String userId) throws FileNotFoundException {
        Model modelToQuery = ModelFactory.createDefaultModel();
        List<FoodRdf> foodRDFS = new ArrayList<>();
        File file = new File("food" + userId + ".rdf");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            modelToQuery.read(reader, null);
            ResIterator resIterator = modelToQuery.listResourcesWithProperty(FoodRdf.nameRdf);
            while (resIterator.hasNext()) {
                Resource resource = resIterator.nextResource();
                foodRDFS.add(fromRdfToFood(modelToQuery, resource));
            }

            return foodRDFS.toArray(new FoodRdf[0]);
        } else {
            return null;
        }
    }

    @Override
    public FoodRdf update(FoodRdf foodRDF, String foodId, String userId) throws FileNotFoundException {
        Model modelToQuery = ModelFactory.createDefaultModel();

        File file = new File("food" + userId + ".rdf");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            modelToQuery.read(reader, null);
        } else {
            return null;
        }

        Resource r = null;
        ResIterator resIterator = modelToQuery.listResourcesWithProperty(FoodRdf.idRdf, foodId);
        while (resIterator.hasNext()) {
            r = resIterator.nextResource();
        }
        if (r == null) {
            return null;
        }
        StmtIterator stmtIterator = r.listProperties();

        modelToQuery.remove(stmtIterator);

        Resource updatedResource = addResourceToModel(foodRDF, modelToQuery);


        ResIterator updateAllGoodWIthIterator = modelToQuery.listSubjectsWithProperty(FoodRdf.isGoodWithRdf, r);
        while (updateAllGoodWIthIterator.hasNext()) {
            Resource res = updateAllGoodWIthIterator.nextResource();
            res.addProperty(FoodRdf.isGoodWithRdf, updatedResource);

        }
        modelToQuery.removeAll(null, FoodRdf.isGoodWithRdf, r);

        Model newTempModel = ModelFactory.createDefaultModel();

        addResourceToModel(foodRDF, newTempModel);
        StringWriter writer = new StringWriter();
        newTempModel.write(writer, "RDF/XML");
        foodRDF.setRdfOutput(writer.toString());

        try (OutputStream out = new FileOutputStream("food" + userId + ".rdf")) {
            modelToQuery.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return foodRDF;
    }

    @Override
    public List<FoodRdf> getRdfFoodByStatement(Property property, String object, String userId) throws FileNotFoundException {
        Model modelToQuery = ModelFactory.createDefaultModel();
        List<FoodRdf> updatedFood = new ArrayList<>();

        File file = new File("food" + userId + ".rdf");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            modelToQuery.read(reader, null);
        } else {
            return null;
        }
        ResIterator resIterator = modelToQuery.listSubjectsWithProperty(property, object);
        while (resIterator.hasNext()) {
            Resource r = resIterator.nextResource();
            FoodRdf foodRdf = fromRdfToFood(modelToQuery, r);
            updatedFood.add(foodRdf);
        }
        return updatedFood;
    }


    private Resource addResourceToModel(FoodRdf foodRDF, Model model) {
        model.setNsPrefix(FoodRdf.NSPrefix, FoodRdf.foodUri);
        String foodName = foodRDF.getName().replaceAll("\\s", "_");
        Resource food = model.createResource(FoodRdf.foodUri + foodName);
        food.addProperty(FoodRdf.caloriesPer100Rdf, foodRDF.getCaloriesPer100().toString());
        food.addProperty(FoodRdf.carbsPer100Rdf, foodRDF.getCarbsPer100().toString());
        food.addProperty(FoodRdf.fatsPer100Rdf, foodRDF.getFatsPer100().toString());
        food.addProperty(FoodRdf.proteinsPer100Rdf, foodRDF.getProteinsPer100().toString());
        food.addProperty(FoodRdf.nameRdf, foodRDF.getName());
        food.addProperty(FoodRdf.vitaminsPer100Rdf, foodRDF.getVitaminsPer100().toString());
        food.addProperty(FoodRdf.saltsPer100Rdf, foodRDF.getSaltsPer100().toString());
        food.addProperty(FoodRdf.descriptionRdf, foodRDF.getDescription());
        food.addProperty(FoodRdf.timeStampRdf, foodRDF.getTimeStamp().toString());
        food.addProperty(FoodRdf.imageUrlRdf, foodRDF.getImageUrl());
        food.addProperty(FoodRdf.typeRdf, foodRDF.getType());
        food.addProperty(FoodRdf.idRdf, foodRDF.getId());


        for (String foodNameGoodWith : foodRDF.getGoodWith()) {
            String parsedFoodName = foodNameGoodWith.split(FoodRdf.foodUri)[0];
            String parsedFoodNameNoWhiteSpaces = parsedFoodName.replaceAll("\\s", "_");
            ResIterator rsIterator = model.listSubjectsWithProperty(FoodRdf.nameRdf, parsedFoodNameNoWhiteSpaces);
            if (rsIterator.hasNext()) {
                Resource r = rsIterator.nextResource();
                Statement statement = ResourceFactory.createStatement(food, FoodRdf.isGoodWithRdf, r);
                model.add(statement);
                while (rsIterator.hasNext()) {
                    Resource res = rsIterator.nextResource();
                    Statement st = ResourceFactory.createStatement(food, FoodRdf.isGoodWithRdf, res);
                    model.add(st);
                }
            } else {
                Resource foodNotYetCreated = model.createResource(FoodRdf.foodUri + parsedFoodNameNoWhiteSpaces);
                Statement s = ResourceFactory.createStatement(food, FoodRdf.isGoodWithRdf, foodNotYetCreated);
                model.add(s);
            }

        }
        for (String goodSynergyWith : foodRDF.getGoodSinergyWith()) {
            String parsedFoodName = goodSynergyWith.split(FoodRdf.foodUri)[0];
            String parsedFoodNameNoWhiteSpaces = parsedFoodName.replaceAll("\\s", "_");
            ResIterator rsIterator = model.listSubjectsWithProperty(FoodRdf.nameRdf, parsedFoodNameNoWhiteSpaces);
            if (rsIterator.hasNext()) {
                Resource r = rsIterator.nextResource();
                Statement statement = ResourceFactory.createStatement(food, FoodRdf.goodSynergyWithRdf, r);
                model.add(statement);
                while (rsIterator.hasNext()) {
                    Resource res = rsIterator.nextResource();
                    Statement st = ResourceFactory.createStatement(food, FoodRdf.goodSynergyWithRdf, res);
                    model.add(st);
                }
            } else {
                Resource foodNotYetCreated = model.createResource(FoodRdf.foodUri + parsedFoodNameNoWhiteSpaces);
                Statement s = ResourceFactory.createStatement(food, FoodRdf.goodSynergyWithRdf, foodNotYetCreated);
                model.add(s);
            }

        }
        for (String foodBestEatenAt : foodRDF.getBestEatenAt()) {
            String parsedFoodName = foodBestEatenAt.split(FoodRdf.foodUri)[0];
            String parsedFoodNameNoWhiteSpaces = parsedFoodName.replaceAll("\\s", "_");
            ResIterator rsIterator = model.listSubjectsWithProperty(FoodRdf.nameRdf, parsedFoodNameNoWhiteSpaces);
            if (rsIterator.hasNext()) {
                Resource r = rsIterator.nextResource();
                Statement statement = ResourceFactory.createStatement(food, FoodRdf.bestEatenAtRdf, r);
                model.add(statement);
                while (rsIterator.hasNext()) {
                    Resource res = rsIterator.nextResource();
                    Statement st = ResourceFactory.createStatement(food, FoodRdf.bestEatenAtRdf, res);
                    model.add(st);
                }
            } else {
                Resource foodNotYetCreated = model.createResource(FoodRdf.foodUri + parsedFoodNameNoWhiteSpaces);
                Statement s = ResourceFactory.createStatement(food, FoodRdf.bestEatenAtRdf, foodNotYetCreated);
                model.add(s);
            }

        }
        return food;
    }

    private FoodRdf fromRdfToFood(Model modelToQuery, Resource r) {
        StmtIterator stmtIterator = modelToQuery.listStatements(r, null, (RDFNode) null);
        FoodRdf foodRdf = new FoodRdf();
//        foodRdf.setName(r.getProperty(FoodRdf.nameRdf).);
        if (r != null && stmtIterator.hasNext()) {
            String name = r.getProperty(FoodRdf.nameRdf).getObject().toString();
            String description = r.getProperty(FoodRdf.descriptionRdf).getObject().toString();
            Number vitamins = Double.parseDouble(r.getProperty(FoodRdf.vitaminsPer100Rdf).getObject().toString());
            Number proteins = Double.parseDouble(r.getProperty(FoodRdf.proteinsPer100Rdf).getObject().toString());
            Number fats = Double.parseDouble(r.getProperty(FoodRdf.fatsPer100Rdf).getObject().toString());
            Number salts = Double.parseDouble(r.getProperty(FoodRdf.saltsPer100Rdf).getObject().toString());
            Number carbs = Double.parseDouble(r.getProperty(FoodRdf.carbsPer100Rdf).getObject().toString());
            Number caloriesPer100 = Double.parseDouble(r.getProperty(FoodRdf.caloriesPer100Rdf).getObject().toString());
            String imageUrl = r.getProperty(FoodRdf.imageUrlRdf).getObject().toString();
            Number timeStamp = Long.parseLong(r.getProperty(FoodRdf.timeStampRdf).getObject().toString());
            String type = r.getProperty(FoodRdf.typeRdf).getObject().toString();
            String id = r.getProperty(FoodRdf.idRdf).getObject().toString();
            foodRdf.setName(name);
            foodRdf.setCaloriesPer100(caloriesPer100);
            foodRdf.setDescription(description);
            foodRdf.setVitaminsPer100(vitamins);
            foodRdf.setProteinsPer100(proteins);
            foodRdf.setFatsPer100(fats);
            foodRdf.setSaltsPer100(salts);
            foodRdf.setCarbsPer100(carbs);
            foodRdf.setImageUrl(imageUrl);
            foodRdf.setTimeStamp(timeStamp);
            foodRdf.setType(type);
            foodRdf.setId(id);
            StmtIterator stmtIteratorGoodWith = modelToQuery.listStatements(r, FoodRdf.isGoodWithRdf, (RDFNode) null);
            StmtIterator stmtIteratorBestEatenAt = modelToQuery.listStatements(r, FoodRdf.bestEatenAtRdf, (RDFNode) null);
            StmtIterator stmtIteratorGoodSynergyWith = modelToQuery.listStatements(r, FoodRdf.goodSynergyWithRdf, (RDFNode) null);
            List<String> goodWiths = new ArrayList<>();
            while (stmtIteratorGoodWith.hasNext()) {
                Statement s = stmtIteratorGoodWith.nextStatement();
                String goodWithName = s.getObject().toString().split(FoodRdf.foodUri)[1];
                String formattedName = goodWithName.replaceAll("_", " ");
                goodWiths.add(formattedName);

            }
            List<String> bestEatenAt = new ArrayList<>();
            while (stmtIteratorBestEatenAt.hasNext()) {
                Statement s = stmtIteratorBestEatenAt.nextStatement();
                String bestEatenName = s.getObject().toString().split(FoodRdf.foodUri)[1];
                String formattedName = bestEatenName.replaceAll("_", " ");
                bestEatenAt.add(formattedName);

            }
            List<String> goodSinergyWith = new ArrayList<>();
            while (stmtIteratorGoodSynergyWith.hasNext()) {
                Statement s = stmtIteratorGoodSynergyWith.nextStatement();
                String goodSinergyName = s.getObject().toString().split(FoodRdf.foodUri)[1];
                String formattedName = goodSinergyName.replaceAll("_", " ");
                goodSinergyWith.add(formattedName);

            }
            foodRdf.setGoodSinergyWith(goodSinergyWith.toArray(new String[0]));
            foodRdf.setBestEatenAt(bestEatenAt.toArray(new String[0]));
            foodRdf.setGoodWith(goodWiths.toArray(new String[0]));
            Model m = ModelFactory.createDefaultModel();
            addResourceToModel(foodRdf, m);
            StringWriter writer = new StringWriter();
            m.write(writer);
            foodRdf.setRdfOutput(writer.toString());
            return foodRdf;

        } else {
            return null;
        }
    }
}
