package com.example.fireAuth_REST_API.repository;

import com.example.fireAuth_REST_API.model.Album;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlbumRepository extends MongoRepository<Album, String> {
}
