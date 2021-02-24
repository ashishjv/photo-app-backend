package com.example.fireAuth_REST_API.service;

import com.example.fireAuth_REST_API.model.Photo;
import com.example.fireAuth_REST_API.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    public Photo getPhotoById(String photoId) {
        return photoRepository.findById(photoId).get();
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public Photo updatePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public String deletePhoto(String photoId) {
        photoRepository.deleteById(photoId);
        return "Deletion completed";
    }

    public List<Photo> getPhotoFromAlbum(String albumId){
        return photoRepository.findByAlbumId(albumId);
    }
}
