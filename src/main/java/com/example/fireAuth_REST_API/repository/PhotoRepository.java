package com.example.fireAuth_REST_API.repository;

import com.example.fireAuth_REST_API.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}
