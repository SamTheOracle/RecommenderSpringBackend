package com.app.recommender.user.Persistence;

import com.app.recommender.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {

    List<User> findUsersByEmail(String email);

    User findUserById(String id);

    User findUserByUserName(String userName);

    User findUserByEmail(String email);

}
