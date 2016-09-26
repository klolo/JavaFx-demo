package com.word_trainer.repository;

import com.word_trainer.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by klolo on 26.09.16.
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findByFirstName(String firstName);

}
