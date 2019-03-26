package com.app.recommender.user;

import com.app.recommender.Model.*;

import java.util.List;

public interface UserService {

    boolean checkIfUserAlreadyExists(String email);

    User saveNewUser(User user) throws UserAlreadyExistException;

    User getUser(String email) throws UserNotFoundException, ServerErrorException;

    User getUserById(String id) throws UserNotFoundException, ServerErrorException;

    User updateNewUser(User user) throws UserNotFoundException, ServerErrorException;

    List<User> getAllNutritionistPatients(String nutritionistId) throws ServerErrorException, UserNotFoundException, PatientsNotFoundException;

    List<User> getAllPatients();
}
