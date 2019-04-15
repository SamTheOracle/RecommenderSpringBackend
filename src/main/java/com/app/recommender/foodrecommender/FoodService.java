package com.app.recommender.foodrecommender;

import com.app.recommender.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service("foodservice")
public class FoodService implements IFoodService {

    @Autowired
    IFoodRecommenderRepository rdfRepository;

    @Override
    public List<FoodRdf> recommendFood(String selectedFood, String userId) throws FileNotFoundException {

        return rdfRepository.getRdfFoodForRecommendation(selectedFood, userId);
    }

    @Override
    public FoodRdf getFoodByName(String foodName, String outputType, String userId) throws IOException {
        FoodRdf food = null;
        if (outputType.equalsIgnoreCase("RDF")) {
            food = this.rdfRepository.getFoodById(foodName, userId);
        } else {

        }
        return food;
    }

    @Override
    public FoodRdf createNewRdfFood(FoodRdf foodRDF, String userId) throws IOException, FoodRdfAlreadyCreatedException {
        FoodRdf food = rdfRepository.getFoodById(foodRDF.getId(), userId);
        if (food != null) {
            throw new FoodRdfAlreadyCreatedException("Error: food " + foodRDF.getName() + " already created");
        }
        return rdfRepository.createRdfFood(foodRDF, userId);
    }

    @Override
    public FoodRdf[] getAllFood(String userId) throws FileNotFoundException, FoodRdfNotFoundException {
        FoodRdf[] food = rdfRepository.getAllFoodFromOntology(userId);
        if (food == null) {
            throw new FoodRdfNotFoundException("Error: food not found");
        }
        return food;
    }

    @Override
    public FoodRdf updateFood(FoodRdf foodRDF, String foodId, String userId) throws IOException, FoodRdfNotFoundException {
        String foodName = foodRDF.getId();
        if (foodName == null) {
            throw new FoodRdfNotFoundException("Error, bad request: there is no name in food");
        }
        FoodRdf f = rdfRepository.getFoodById(foodId, userId);
        if (f == null) {
            throw new FoodRdfNotFoundException("Error: " + foodName + " could not be found");
        }
        f = rdfRepository.update(foodRDF, foodId, userId);
        return f;
    }

    @Override
    public List<FoodRdf> getGeneralFoodRecommendation(Diet diet, String userId) throws FileNotFoundException {
        AtomicReference<Double> fruitsAndVeggiesCalories = new AtomicReference<>(0.0);
        List<FoodRdf> allRecommendedFood = new ArrayList<>();
        diet.getDailyFood().forEach((day, meal) -> meal.forEach(m -> {
            double totalVeggiesAndFruitsCaloriesCountPerMeal = m.getAllFoodEntries().stream().filter(f ->
                    f.getType().equalsIgnoreCase("Fruits")
                            || f.getType().equalsIgnoreCase("Vegetables"))
                    .mapToDouble(f -> f.getQuantity().doubleValue()).sum();
            fruitsAndVeggiesCalories.updateAndGet(v -> v + totalVeggiesAndFruitsCaloriesCountPerMeal);
        }));
        if (fruitsAndVeggiesCalories.get() < 600.0) {
            List<FoodRdf> foodRdfListVegetables = rdfRepository.getRdfFoodByStatement(FoodRdf.typeRdf, "Vegetables", userId);
            List<FoodRdf> foodRdfListFruits = rdfRepository.getRdfFoodByStatement(FoodRdf.typeRdf, "Fruits", userId);
            List<Food> allDietFood = new ArrayList<>();
            diet.getDailyFood().forEach((day,meals)->{
                meals.forEach(meal->allDietFood.addAll(meal.getAllFoodEntries()));
            });

            allRecommendedFood.addAll(foodRdfListFruits);
            allRecommendedFood.addAll(foodRdfListVegetables);

           // allRecommendedFood = allRecommendedFood.stream().filter(foodRdf -> allDietFood.stream().noneMatch(f->f.getId().equalsIgnoreCase(foodRdf.getId()))).collect(Collectors.toList());

            return allRecommendedFood;
        }

        return null;
    }

    @Override
    public List<FoodRdf> getGeneralFoodRecommendationForMeat(Diet diet, String userId, double rightAmountOfProteins) throws FileNotFoundException {
        AtomicReference<Double> meatCalories = new AtomicReference<>(0.0);
        List<FoodRdf> allRecommendedFood = new ArrayList<>();
        diet.getDailyFood().forEach((day, meal) -> meal.forEach(m -> {
            double totalMeatAndFishCaloriesCountPerMeal = m.getAllFoodEntries().stream()
                    .mapToDouble(f -> f.getQuantity().doubleValue()).sum();
            meatCalories.updateAndGet(v -> v + totalMeatAndFishCaloriesCountPerMeal);
        }));
        double averageAmountOfProteins = Math.round(meatCalories.get() / 7);
        if (averageAmountOfProteins < rightAmountOfProteins) {
            List<FoodRdf> foodRdfListMeat = rdfRepository.getRdfFoodByStatement(FoodRdf.typeRdf, "Meat", userId);
            List<FoodRdf> foodRdfListFish = rdfRepository.getRdfFoodByStatement(FoodRdf.typeRdf, "Fish", userId);
            List<Food> allDietFood = new ArrayList<>();
            diet.getDailyFood().forEach((day,meals)->{
                meals.forEach(meal->allDietFood.addAll(meal.getAllFoodEntries()));
            });
            allRecommendedFood.addAll(foodRdfListMeat);
            allRecommendedFood.addAll(foodRdfListFish);
            //allRecommendedFood = allRecommendedFood.stream().filter(foodRdf -> allDietFood.stream().noneMatch(f->
             //       f.getId().equalsIgnoreCase(foodRdf.getId()))).collect(Collectors.toList());

            return allRecommendedFood;
        }

        return null;
    }

    @Override
    public List<FoodRdf> getGeneralFoodRecommendationForCarbohydrates(Diet diet, String userId) throws FileNotFoundException {

        AtomicReference<Double> pastaQuantity = new AtomicReference<>(0.0);
        List<FoodRdf> allRecommendedFood = new ArrayList<>();
        diet.getDailyFood().forEach((day, meal) -> meal.stream().filter(m -> m.getMealType().equalsIgnoreCase("Lunch") ||
                m.getMealType().equalsIgnoreCase("Dinner")).forEach(m -> {
            double totalPastaQuantityCountPerMeal = m.getAllFoodEntries().stream().filter(f -> f.getType().equalsIgnoreCase("Pasta")
                    || f.getType().equalsIgnoreCase("Bakery and Cereal"))
                    .mapToDouble(f -> f.getQuantity().doubleValue()).sum();
            pastaQuantity.updateAndGet(v -> v + totalPastaQuantityCountPerMeal);
        }));
        if (pastaQuantity.get() < 12) {
            List<FoodRdf> foodRdfListMeat = rdfRepository.getRdfFoodByStatement(FoodRdf.typeRdf, "Meat", userId);
            List<FoodRdf> foodRdfListFish = rdfRepository.getRdfFoodByStatement(FoodRdf.typeRdf, "Fish", userId);

            allRecommendedFood.addAll(foodRdfListMeat);
            allRecommendedFood.addAll(foodRdfListFish);
            return allRecommendedFood;
        }

        return null;
    }

}
