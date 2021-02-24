package com.example.fireAuth_REST_API.repository;

import com.example.fireAuth_REST_API.model.FileMongoDB;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileMongoDBRepository extends MongoRepository<FileMongoDB, String> {
}
