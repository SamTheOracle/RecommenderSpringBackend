package com.app.recommender.diet;

import com.app.recommender.Model.*;
import com.app.recommender.diet.Persistence.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DietServiceImpl implements DietService {

    @Autowired
    private DietRepository dietRepository;

    @Override

    public Diet createNewDiet(Diet diet) throws DietAlreadyExistException {
        try {

            List<Diet> diets = this.dietRepository.findByUserId(diet.getUserId());
            if (diets.isEmpty()) {
                diet.setTimeStamp(LocalDate.now());
                Map<String, List<Meal>> meals = new HashMap<>();
                for (DayOfWeek day : DayOfWeek.values()) {
                    List<Meal> mealsArr = new ArrayList<>();

                    for (MealType mealType : MealType.values()) {
                        Meal m = new Meal();
                        m.setMealType(mealType.getValueToDisplay());
                        m.setAllFoodEntries(new ArrayList<>());
                        mealsArr.add(m);
                    }


                    meals.put(day.getDisplayName(TextStyle.FULL,
                            Locale.US), mealsArr);
                    diet.setDailyFood(meals);

                }
                dietRepository.insert(diet);
                return diet;
            }
            Optional<Diet> d = diets.stream().filter(userDiet -> userDiet.getName().equals(diet.getName())).findAny();
            if (d.isPresent()) {
                throw new DietAlreadyExistException("Diet with name " + diet.getName() + " already exists");
            }
            System.out.println(LocalDate.now());
            diet.setTimeStamp(LocalDate.now());
            Map<String, List<Meal>> meals = new HashMap<>();
            for (DayOfWeek day : DayOfWeek.values()) {
                List<Meal> mealsArr = new ArrayList<>();

                for (MealType mealType : MealType.values()) {
                    Meal m = new Meal();
                    m.setMealType(mealType.getValueToDisplay());
                    m.setAllFoodEntries(new ArrayList<>());
                    mealsArr.add(m);
                }


                meals.put(day.getDisplayName(TextStyle.FULL,
                        Locale.US), mealsArr);
                diet.setDailyFood(meals);

            }
            dietRepository.insert(diet);
            return diet;

        } catch (IncorrectResultSizeDataAccessException e) {
            throw new DietAlreadyExistException("Diet with name " + diet.getName() + " already exists");

        }

    }

    @Override
    public List<Food> getAllFood() {
        return null;
    }

    @Override
    public Food getFood(String name) {
        return null;
    }

    @Override
    public Diet updateDiet(Diet diet) {
        this.dietRepository.save(diet);

        return dietRepository.findDietByName(diet.getName());
    }

    @Override
    public Diet removeFood(Diet food) {
        return null;
    }

    @Override
    public Diet getCurrentDietByUserId(String userId) throws NoDietHistoryException {
        List<Diet> diets = dietRepository.findByUserId(userId);
        if (diets.isEmpty()) {
            throw new NoDietHistoryException("There are no recorded diets for this userId " + userId);
        } else {
            diets.sort(Comparator.comparing(Diet::getTimeStamp).reversed());
            return diets.stream().findFirst().get();
        }

    }

    @Override
    public Diet getDietByDietName(String dietName, String userId) throws NoDietHistoryException, DietNotFoundException {
        List<Diet> diets = this.dietRepository.findByUserId(userId);
        if (diets.isEmpty()) {
            throw new NoDietHistoryException("There are no recorded diets for this userId " + userId);
        }
        Optional<Diet> diet = diets.stream().filter(d -> d.getName().equals(dietName)).findAny();
        if (!diet.isPresent()) {
            throw new DietNotFoundException("No diet with name: " + dietName + " has been found for user " + userId);
        }
        return diet.get();

    }

    @Override
    public List<DietHistory> getRecentDiets(String monthName, String userId, String year) throws NumberFormatException,NoDietHistoryException{
        List<Diet> diets = this.dietRepository.findByUserId(userId);
        List<Diet> monthlyDiets = diets.stream()
                .filter(diet -> diet.getTimeStamp().getYear() == Integer.parseInt(year) && diet.getTimeStamp().getMonth().toString().equalsIgnoreCase(monthName)).collect(Collectors.toList());
        List<DietHistory> history = new ArrayList<>();
        monthlyDiets.forEach(diet->{
            String timeStamp = diet.getTimeStamp().toString();
            String dietName = diet.getName();
            history.add(new DietHistory(timeStamp,dietName,diet.getTotalCalories()));
        });
        history.sort(Comparator.comparing(DietHistory::getTimeStamp).reversed());
        return history;
    }

    @Override
    public List<DietHistory> getDietsByYear(String userId, String year) throws NumberFormatException,NoDietHistoryException{
        List<Diet> diets = this.dietRepository.findByUserId(userId);
        if(diets.isEmpty()){
            throw new NoDietHistoryException("No recorded diets for user "+userId);
        }
        int yearToFetch = 0;
        try{
            yearToFetch = Integer.parseInt(year);
        }
        catch(NumberFormatException e){
            throw new NumberFormatException("Error. Year is not correct");
        }

        int finalYearToFetch = yearToFetch;
        List<Diet> yearDiets = diets.stream().filter(diet -> diet.getTimeStamp().getYear() == finalYearToFetch).collect(Collectors.toList());
        List<DietHistory> yearHistory = new ArrayList<>();
        yearDiets.forEach(diet -> yearHistory.add(new DietHistory(diet.getTimeStamp().toString(),diet.getName(),diet.getTotalCalories())));
        return yearHistory;
    }

}
