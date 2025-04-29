package com.rlb.oc.repository;

import com.rlb.oc.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
