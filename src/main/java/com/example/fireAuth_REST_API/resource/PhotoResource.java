package com.example.fireAuth_REST_API.resource;

import com.example.fireAuth_REST_API.exception.InvalidTokenException;
import com.example.fireAuth_REST_API.exception.UserNotAuthorizedException;
import com.example.fireAuth_REST_API.model.FirebaseUser;
import com.example.fireAuth_REST_API.model.Photo;
import com.example.fireAuth_REST_API.service.FirebaseService;
import com.example.fireAuth_REST_API.service.PhotoService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoResource {

    @Autowired
    private PhotoService photoService;

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping
    public List<Photo> getAllPhotos() {
        return photoService.getAllPhotos();
    }

    @GetMapping("/{id}")
    public Photo getPhotoById(@PathVariable(name = "id") String photoId,
                              @RequestHeader(name = "idToken") String idToken) {
        return photoService.getPhotoById(photoId);
    }

    @PostMapping
    public Photo savePhoto(@RequestBody @Valid Photo photo,
                           @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {

            photo.setCreatedBy(firebaseUser.getEmail());

            if (photo.getDateCreated() == null) {
                photo.setDateCreated(java.time.LocalDate.now());
            }
            return photoService.savePhoto(photo);
        } else {
            throw new InvalidTokenException();
        }
    }

    @PutMapping
    public Photo updatePhoto(@RequestBody @Valid Photo photo,
                             @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            if (!firebaseUser.getEmail().equalsIgnoreCase(photo.getCreatedBy())) {
                throw new UserNotAuthorizedException(
                        "FireBaseUser.email: " + firebaseUser.getEmail() + "\n" +
                                "Photo.createdBy: " + photo.getCreatedBy()
                );
            } else {
                return photoService.updatePhoto(photo);
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @DeleteMapping
    public String deletePhoto(@RequestParam(name = "photoId") String photoId,
                              @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        Photo photo = photoService.getPhotoById(photoId);

        if (firebaseUser != null) {
            if (!firebaseUser.getEmail().equalsIgnoreCase(photo.getCreatedBy())) {
                throw new UserNotAuthorizedException(
                        "FireBaseUser.email: " + firebaseUser.getEmail() + "\n" +
                                "Photo.createdBy: " + photo.getCreatedBy()
                );
            } else {
                return photoService.deletePhoto(photoId);
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @GetMapping("/album")
    public List<Photo> getPhotosFromAlbum(@RequestParam(name = "albumId") String albumId,
                                         @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            return photoService.getPhotoFromAlbum(albumId);
        } else {
            throw new InvalidTokenException();
        }
    }

}
