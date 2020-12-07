package com.example.fireAuth_REST_API.resource;

import com.example.fireAuth_REST_API.exception.InvalidTokenException;
import com.example.fireAuth_REST_API.exception.UserNotAuthorizedException;
import com.example.fireAuth_REST_API.model.Album;
import com.example.fireAuth_REST_API.model.FirebaseUser;
import com.example.fireAuth_REST_API.service.AlbumService;
import com.example.fireAuth_REST_API.service.FirebaseService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/album")
public class AlbumResource {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @GetMapping("/id")
    public Album getAlbumById(@RequestParam(name = "albumId") String albumId) {
        return albumService.getAlbumById(albumId);
    }

    @PostMapping
    public Album saveAlbum(@RequestBody @Valid Album album,
                           @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            album.setCreatedBy(firebaseUser.getEmail());
            if (album.getDateCreated() == null) {
                album.setDateCreated(java.time.LocalDate.now());
            }
            return albumService.saveAlbum(album);
        } else {
            throw new InvalidTokenException();
        }
    }

    @PutMapping
    public Album updateAlbum(@RequestBody @Valid Album album,
                             @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            if (!firebaseUser.getEmail().equalsIgnoreCase(album.getCreatedBy())) {
                throw new UserNotAuthorizedException(
                        "FireBaseUser.email: " + firebaseUser.getEmail() + "\n" +
                                "Album.createdBy: " + album.getCreatedBy()
                );
            } else {
                return albumService.updateAlbum(album);
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @DeleteMapping
    public String deleteAlbum(@RequestParam(name = "albumId") String albumId,
                              @RequestHeader(name = "idToken") String idToken)
            throws
            IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        Album album = albumService.getAlbumById(albumId);

        if (firebaseUser != null) {
            if (!firebaseUser.getEmail().equalsIgnoreCase(album.getCreatedBy())) {
                throw new UserNotAuthorizedException(
                        "FireBaseUser.email: " + firebaseUser.getEmail() + "\n" +
                                "Album.createdBy: " + album.getCreatedBy()
                );
            } else {
                return albumService.deleteAlbum(albumId);
            }
        } else {
            throw new InvalidTokenException();
        }
    }

}
