package com.app.recommender.user;

import com.app.recommender.Model.ServerErrorException;
import com.app.recommender.Model.User;
import com.app.recommender.Model.UserAlreadyExistException;
import com.app.recommender.Model.UsernameNotFoundException;
import com.app.recommender.user.Persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            repository.insert(user);
            return user;
        } else {
            throw new UserAlreadyExistException("Error. User already exists for email: " + user.getEmail());
        }
    }

    @Override
    public User getUser(String email) throws UsernameNotFoundException, ServerErrorException {

        try {
            User user = repository.findUserByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("Error. User not found for email: " + email);
            }
            return user;
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getErrorMessage());
        } catch (Exception e) {
            throw new ServerErrorException(e.getMessage(), e.getCause());
        }


    }

    @Override
    public User getUserById(String id) throws UsernameNotFoundException, ServerErrorException {
        try {
            User user = repository.findUserById(id);
            if (user == null) {
                throw new UsernameNotFoundException("Error. User not found");
            } else {
                return user;
            }
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getErrorMessage());
        } catch (Exception e) {
            throw new ServerErrorException(e.getMessage(), e);
        }
    }

    @Override
    public User updateNewUser(User user) throws UsernameNotFoundException, ServerErrorException {
        try {
            User userToDelete = getUser(user.getEmail());
            repository.delete(userToDelete);
            user.computeBMR();
            repository.insert(user);
            return user;
        } catch (ServerErrorException e) {
            throw new ServerErrorException(e.getMessage(), e);
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(e.getErrorMessage());
        }
    }
}