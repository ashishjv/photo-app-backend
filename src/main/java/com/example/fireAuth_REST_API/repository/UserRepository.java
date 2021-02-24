package com.example.fireAuth_REST_API.repository;


import com.example.fireAuth_REST_API.model.Album;
import com.example.fireAuth_REST_API.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}
