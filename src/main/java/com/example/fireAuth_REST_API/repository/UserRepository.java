package com.example.fireAuth_REST_API.repository;


import com.example.fireAuth_REST_API.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
