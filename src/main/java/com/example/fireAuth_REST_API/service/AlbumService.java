package com.example.fireAuth_REST_API.service;

import com.example.fireAuth_REST_API.model.Album;
import com.example.fireAuth_REST_API.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public List<Album> getAllAlbumsByUser(String createdBy){
        return albumRepository.findByCreatedBy(createdBy);
    }

    public Album getAlbumById(String albumId) {
        return albumRepository.findById(albumId).get();
    }

    public Album saveAlbum(Album album) {
        return albumRepository.save(album);
    }

    /* Saving an object with pre-existing id only updates its values */
    public Album updateAlbum(Album album) {
        return albumRepository.save(album);
    }

    public String deleteAlbum(String albumId) {
        albumRepository.deleteById(albumId);
        return "Deletion completed";
    }
}
