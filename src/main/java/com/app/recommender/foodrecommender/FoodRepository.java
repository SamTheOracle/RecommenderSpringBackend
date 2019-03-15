package com.app.recommender.foodrecommender;

import com.app.recommender.Model.FoodRdfNotFoundException;
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
    public FoodRdf createRdfFood(FoodRdf foodRDF) throws FileNotFoundException {
        File file = new File("food.rdf");
        Model model = ModelFactory.createDefaultModel();
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            model.read(reader, null);
        }

        addResourceToModel(foodRDF, model);


        try (OutputStream out = new FileOutputStream("food.rdf")) {
            model.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Model modelForOutput = ModelFactory.createDefaultModel();
        addResourceToModel(foodRDF, modelForOutput);
        StringWriter writer = new StringWriter();
        model.write(writer, "RDF/XML");
        foodRDF.setRdfOutput(writer.toString());
        return foodRDF;
    }


    @Override
    public List<FoodRdf> getRdfFoodForRecommendation(String name) throws FileNotFoundException {
        List<FoodRdf> recommendedFoods = new ArrayList<>();

        Model modelToQuery = ModelFactory.createDefaultModel();

        File file = new File("food.rdf");
        FileReader reader = new FileReader(file);
        modelToQuery.read(reader, null);
        Resource r = modelToQuery.getResource(FoodRdf.foodUri + name);
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
    public FoodRdf getFoodByName(String foodName) throws IOException {
        Model modelToQuery = ModelFactory.createDefaultModel();

        File file = new File("food.rdf");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            modelToQuery.read(reader, null);
        } else {
            return null;
        }


        Resource r = modelToQuery.getResource(FoodRdf.foodUri + foodName);
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
    public FoodRdf[] getAllFoodFromOntology() throws FileNotFoundException {
        Model modelToQuery = ModelFactory.createDefaultModel();
        List<FoodRdf> foodRDFS = new ArrayList<>();
        File file = new File("food.rdf");
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
    public FoodRdf update(FoodRdf foodRDF) throws FoodRdfNotFoundException, FileNotFoundException {
        Model modelToQuery = ModelFactory.createDefaultModel();

        File file = new File("food.rdf");
        if (file.exists()) {
            FileReader reader = new FileReader(file);
            modelToQuery.read(reader, null);
        } else {
            return null;
        }

        Resource r = modelToQuery.getResource(FoodRdf.foodUri + foodRDF.getName());
        StmtIterator stmtIterator = r.listProperties();

        modelToQuery.remove(stmtIterator);

        addResourceToModel(foodRDF, modelToQuery);

        Model newTempModel = ModelFactory.createDefaultModel();

        addResourceToModel(foodRDF, newTempModel);
        StringWriter writer = new StringWriter();
        newTempModel.write(writer, "RDF/XML");
        foodRDF.setRdfOutput(writer.toString());

        try (OutputStream out = new FileOutputStream("food.rdf")) {
            modelToQuery.write(out, "RDF/XML");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return foodRDF;
    }

//    private Model loadModel() {
//        Model model = null;
//
//        ds.begin(ReadWrite.WRITE);
//        try {
//            model = ds.getNamedModel("food");
//            FileManager.get().readModel(model, "food.rdf");
//            model.write(System.out, "RDF/XML");
//
//            ds.commit();
//        } finally {
//
//            ds.end();
//        }
//        return model;
//    }

    private void addResourceToModel(FoodRdf foodRDF, Model model) {
        model.setNsPrefix(FoodRdf.NSPrefix, FoodRdf.foodUri);
        String foodName = foodRDF.getName().replaceAll("\\s", "");
        Resource food = model.createResource(FoodRdf.foodUri + foodName);
//        Resource pineapple = model.createResource(foodUri + "Pineapple");
        food.addProperty(FoodRdf.caloriesPer100Rdf, foodRDF.getCaloriesPer100().toString());
        food.addProperty(FoodRdf.carbsRdf, foodRDF.getCarbs().toString());
        food.addProperty(FoodRdf.fatsRdf, foodRDF.getFats().toString());
        food.addProperty(FoodRdf.proteinsRdf, foodRDF.getProteins().toString());
        food.addProperty(FoodRdf.nameRdf, foodRDF.getName());
        food.addProperty(FoodRdf.vitaminsRdf, foodRDF.getVitamins().toString());
        food.addProperty(FoodRdf.saltsRdf, foodRDF.getSalts().toString());
        food.addProperty(FoodRdf.descriptionRdf, foodRDF.getDescription());
        food.addProperty(FoodRdf.timeStampRdf, foodRDF.getTimeStamp().toString());
        food.addProperty(FoodRdf.imageUrlRdf, foodRDF.getImageUrl());
        food.addProperty(FoodRdf.typeRdf, foodRDF.getType());


        for (String foodNameGoodWith : foodRDF.getGoodWith()) {
            String parsedFoodName = foodNameGoodWith.split(FoodRdf.foodUri)[0];
            ResIterator rsIterator = model.listSubjectsWithProperty(FoodRdf.nameRdf, parsedFoodName);
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
                Resource foodNotYetCreated = model.createResource(FoodRdf.foodUri + parsedFoodName);
                Statement s = ResourceFactory.createStatement(food, FoodRdf.isGoodWithRdf, foodNotYetCreated);
                model.add(s);
            }

        }
        for (String goodSynergyWith : foodRDF.getGoodSinergyWith()) {
            String parsedFoodName = goodSynergyWith.split(FoodRdf.foodUri)[0];
            ResIterator rsIterator = model.listSubjectsWithProperty(FoodRdf.nameRdf, parsedFoodName);
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
                Resource foodNotYetCreated = model.createResource(FoodRdf.foodUri + parsedFoodName);
                Statement s = ResourceFactory.createStatement(food, FoodRdf.goodSynergyWithRdf, foodNotYetCreated);
                model.add(s);
            }

        }
        for (String foodBestEatenAt : foodRDF.getBestEatenAt()) {
            String parsedFoodName = foodBestEatenAt.split(FoodRdf.foodUri)[0];
            ResIterator rsIterator = model.listSubjectsWithProperty(FoodRdf.nameRdf, parsedFoodName);
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
                Resource foodNotYetCreated = model.createResource(FoodRdf.foodUri + parsedFoodName);
                Statement s = ResourceFactory.createStatement(food, FoodRdf.bestEatenAtRdf, foodNotYetCreated);
                model.add(s);
            }

        }
    }

    private FoodRdf fromRdfToFood(Model modelToQuery, Resource r) {
        StmtIterator stmtIterator = modelToQuery.listStatements(r, null, (RDFNode) null);
        FoodRdf foodRdf = new FoodRdf();
//        foodRdf.setName(r.getProperty(FoodRdf.nameRdf).);
        if (r != null && stmtIterator.hasNext()) {
            Statement ssdsa = stmtIterator.nextStatement();
            String name = r.getProperty(FoodRdf.nameRdf).getObject().toString();
            String description = r.getProperty(FoodRdf.descriptionRdf).getObject().toString();
            Number vitamins = Double.parseDouble(r.getProperty(FoodRdf.vitaminsRdf).getObject().toString());
            Number proteins = Double.parseDouble(r.getProperty(FoodRdf.proteinsRdf).getObject().toString());
            Number fats = Double.parseDouble(r.getProperty(FoodRdf.fatsRdf).getObject().toString());
            Number salts = Double.parseDouble(r.getProperty(FoodRdf.saltsRdf).getObject().toString());
            Number carbs = Double.parseDouble(r.getProperty(FoodRdf.carbsRdf).getObject().toString());
            Number caloriesPer100 = Double.parseDouble(r.getProperty(FoodRdf.caloriesPer100Rdf).getObject().toString());
            String imageUrl = r.getProperty(FoodRdf.imageUrlRdf).getObject().toString();
            Number timeStamp = Long.parseLong(r.getProperty(FoodRdf.timeStampRdf).getObject().toString());
            String type = r.getProperty(FoodRdf.typeRdf).getObject().toString();
            foodRdf.setName(name);
            foodRdf.setCaloriesPer100(caloriesPer100);
            foodRdf.setDescription(description);
            foodRdf.setVitamins(vitamins);
            foodRdf.setProteins(proteins);
            foodRdf.setFats(fats);
            foodRdf.setSalts(salts);
            foodRdf.setCarbs(carbs);
            foodRdf.setImageUrl(imageUrl);
            foodRdf.setTimeStamp(timeStamp);
            foodRdf.setType(type);
            StmtIterator stmtIteratorGoodWith = modelToQuery.listStatements(r, FoodRdf.isGoodWithRdf, (RDFNode) null);
            StmtIterator stmtIteratorBestEatenAt = modelToQuery.listStatements(r, FoodRdf.bestEatenAtRdf, (RDFNode) null);
            StmtIterator stmtIteratorGoodSynergyWith = modelToQuery.listStatements(r, FoodRdf.goodSynergyWithRdf, (RDFNode) null);
            List<String> goodWiths = new ArrayList<>();
            while (stmtIteratorGoodWith.hasNext()) {
                Statement s = stmtIteratorGoodWith.nextStatement();
                String goodWithName = s.getObject().toString().split(FoodRdf.foodUri)[1];
                goodWiths.add(goodWithName);

            }
            List<String> bestEatenAt = new ArrayList<>();
            while (stmtIteratorBestEatenAt.hasNext()) {
                Statement s = stmtIteratorBestEatenAt.nextStatement();
                String bestEatenName = s.getObject().toString().split(FoodRdf.foodUri)[1];

                bestEatenAt.add(bestEatenName);

            }
            List<String> goodSinergyWith = new ArrayList<>();
            while (stmtIteratorGoodSynergyWith.hasNext()) {
                Statement s = stmtIteratorGoodSynergyWith.nextStatement();
                String goodSinergyName = s.getObject().toString().split(FoodRdf.foodUri)[1];
                goodSinergyWith.add(goodSinergyName);

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
