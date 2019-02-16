package com.app.recommender.diet;

import com.app.recommender.Model.*;

import java.rmi.UnexpectedException;
import java.util.List;

public interface DietService {

    Diet createNewDiet(Diet diet) throws DietAlreadyExistException;

    List<Food> getAllFood();

    Food getFood(String name);

    Diet updateDiet(Diet diet);

    Diet removeFood(Diet food);

    Diet getCurrentDietByUserId(String userId) throws NoDietHistoryException;

    Diet getDietByDietName(String dietName, String userId) throws DietNotFoundException, NoDietHistoryException;

    List<DietHistory> getRecentDiets(String monthName, String userId, String year) throws DietNotFoundException, NoDietHistoryException;


    List<DietHistory> getDietsByYear(String userId, String year) throws DietNotFoundException, NoDietHistoryException;

    Meal updateDiet(Food food, String dietName, String userId, String day,String mealType) throws UnexpectedException, NoDietHistoryException, DietNotFoundException;
}
