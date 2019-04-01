package com.app.recommender.diet;

import com.app.recommender.Model.*;
import com.app.recommender.goals.DietUpdateGoalMessage;
import com.app.recommender.physicalactivities.DietUpdatePaMessage;

import java.rmi.UnexpectedException;
import java.util.List;

public interface IDietService {

    Diet createNewDiet(Diet diet) throws DietAlreadyExistException;

    List<Food> getAllFood();

    Food getFood(String name);

    Diet updateDiet(Diet diet) throws NoDietHistoryException;

    Diet removeFood(Diet food);

    Diet getCurrentDietByUserId(String userId) throws NoDietHistoryException;

    Diet getDietByDietName(String dietName, String userId) throws DietNotFoundException, NoDietHistoryException;

    List<DietHistory> getRecentDiets(String monthName, String userId, String year) throws DietNotFoundException, NoDietHistoryException;

    List<DietHistory> getDietsByYear(String userId, String year) throws DietNotFoundException, NoDietHistoryException;

    Meal updateDiet(Food food, String dietName, String userId, String day, String mealType) throws UnexpectedException, NoDietHistoryException, DietNotFoundException;

    void updateDietValues(FoodRdf foodToUpdate, String userId);


    void updateDietCurrentPhysicalActivity(DietUpdatePaMessage dietUpdateMessage);

    void updateDietCurrentGoal(DietUpdateGoalMessage dietUpdateGoalMessage);
}
