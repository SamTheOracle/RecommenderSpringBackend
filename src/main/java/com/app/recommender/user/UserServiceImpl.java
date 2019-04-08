package com.app.recommender.user;

import com.app.recommender.Model.*;
import com.app.recommender.user.Persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;


    @Override
    public boolean checkIfUserAlreadyExists(String email) {
        List<User> users = repository.findUsersByEmail(email);


        return !users.isEmpty();


    }

    @Override
    public User saveNewUser(User user) throws UserAlreadyExistException {
        if (!checkIfUserAlreadyExists(user.getEmail())) {
            user.setPatients(new ArrayList<>());
//            user.setImageUrl("https://api.adorable.io/avatars/120/" + new Random().nextInt(5000) + ".png");
            user.setCurrentPatient(null);
            repository.insert(user);
            return user;
        } else {
            throw new UserAlreadyExistException("Error. User already exists for email: " + user.getEmail());
        }
    }

    @Override
    public User getUser(String email) throws UserNotFoundException, ServerErrorException {

        User user = repository.findUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("Error. User not found for email: " + email);
        }
        return user;


    }

    @Override
    public User getUserById(String id) throws UserNotFoundException, ServerErrorException {
        User user = repository.findUserById(id);
        if (user == null) {
            throw new UserNotFoundException("Error. User not found");
        } else {
            return user;
        }

    }

    @Override
    public User updateNewUser(User user) throws UserNotFoundException, ServerErrorException {
        if (!user.getEmail().contains("nutrizionista")) {
            user.computeBMR();


        }
        User userToSendBack = repository.save(user);
        List<User> users = repository.findAll();
        users.stream().filter(u -> u.getPatients() != null && u.getPatients().size() > 0).forEach(u -> {
            Optional<User> optionalUser = u.getPatients().stream().filter(queryUser -> queryUser.getId().equalsIgnoreCase(user.getId())).findAny();
            optionalUser.ifPresent(userToUpdate -> userToUpdate.setImageUrl(user.getImageUrl()));
            User currentPatient = u.getCurrentPatient();
            currentPatient.setImageUrl(user.getImageUrl());
            User debugPurposes = this.repository.save(u);

        });
        return userToSendBack;

    }

    @Override
    public List<User> getAllNutritionistPatients(String nutritionistId) throws ServerErrorException, UserNotFoundException, PatientsNotFoundException {
        User nutritionist = getUserById(nutritionistId);
        if (nutritionist != null && nutritionist.getEmail().contains("nutrizionista")) {
            List<User> patients = new ArrayList<>(nutritionist.getPatients());
            if (patients.size() == 0) {
                throw new PatientsNotFoundException("Error. No patients for nutritionist " + nutritionist.getUserName());

            }
            return patients;
        } else {
            throw new UserNotFoundException("Error. Nutritionist not found for id " + nutritionistId);
        }

    }

    @Override
    public List<User> getAllPatients() {
        List<User> users = this.repository.findAll();

        return users.stream().filter(user -> !user.getEmail().contains("nutrizionista")).collect(Collectors.toList());
    }
}