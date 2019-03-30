package com.app.recommender.Model;

import org.springframework.data.annotation.Id;

public class Record {
    @Id
    private String id;

    private String userId, dietId;

    private double amountOfCalories;


}
