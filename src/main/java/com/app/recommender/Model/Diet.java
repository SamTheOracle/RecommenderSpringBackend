package com.app.recommender.Model;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Diet implements Comparator<Diet> {



    @Id
    private String id;

    private String userId;

    private String name;

    private Map<String, List<Meal>> dailyFood;

    private Map<String, Number> caloriesPerDay;

    private LocalDate timeStamp;

    private Double totalCalories;



    public Diet() {
    }


    public void setTimeStamp(LocalDate timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, List<Meal>> getDailyFood() {
        return dailyFood;
    }

    public void setDailyFood(Map<String, List<Meal>> dailyFood) {
        this.dailyFood = dailyFood;
    }

    public Map<String, Number> getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(Map<String, Number> caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public LocalDate getTimeStamp() {
        return timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Double totalCalories) {
        this.totalCalories = totalCalories;
    }



    @Override
    public int compare(Diet o1, Diet o2) {
        return o1.getTimeStamp().compareTo(o2.getTimeStamp());
    }

    public void updateCalories(String day){
        double dailyCaloriesCount = 0.0;
        for(int i = 0; i < this.dailyFood.get(day).size();i++){
            List<Food> allFood = this.dailyFood.get(day).get(i).getAllFoodEntries();

            for(int j = 0; j < allFood.size();j++){
                dailyCaloriesCount += allFood.get(j).getCalories().doubleValue();
            }
        }
        this.caloriesPerDay.put(day,dailyCaloriesCount);


        updateTotalCalories();

    }

    private void updateTotalCalories(){
        this.getCaloriesPerDay().forEach((key, value) -> {
            this.totalCalories+=value.doubleValue();
        });
    }
}
