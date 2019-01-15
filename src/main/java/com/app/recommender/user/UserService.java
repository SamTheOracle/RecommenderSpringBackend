package com.app.recommender.user;

import com.app.recommender.Model.ServerErrorException;
import com.app.recommender.Model.User;
import com.app.recommender.Model.UserAlreadyExistException;
import com.app.recommender.Model.UsernameNotFoundException;

public interface UserService {

    boolean checkIfUserAlreadyExists(String email);

    User saveNewUser(User user) throws UserAlreadyExistException;

    User getUser(String email) throws UsernameNotFoundException, ServerErrorException;

    User getUserById(String id) throws UsernameNotFoundException, ServerErrorException;

    User updateNewUser(User user) throws UsernameNotFoundException, ServerErrorException;

}
