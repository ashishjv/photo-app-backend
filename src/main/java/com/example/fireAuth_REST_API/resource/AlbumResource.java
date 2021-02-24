package com.example.fireAuth_REST_API.resource;

import com.example.fireAuth_REST_API.exception.InvalidTokenException;
import com.example.fireAuth_REST_API.exception.UserNotAuthorizedException;
import com.example.fireAuth_REST_API.model.Album;
import com.example.fireAuth_REST_API.model.FirebaseUser;
import com.example.fireAuth_REST_API.model.Photo;
import com.example.fireAuth_REST_API.service.AlbumService;
import com.example.fireAuth_REST_API.service.FirebaseService;
import com.example.fireAuth_REST_API.service.PhotoService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/albums")
public class AlbumResource {
    @Autowired
    private AlbumService albumService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private FirebaseService firebaseService;

    private String createdBy;

    @GetMapping
    public List<Album> getAllAlbumsByUser(@RequestHeader(name = "idToken") String idToken)
            throws
            FirebaseAuthException,
            InvalidTokenException,
            IOException {
        this.createdBy = getEmailIdIfAuthenticatedUser(idToken);
        return albumService.getAllAlbumsByUser(this.createdBy);
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
            album.setId(null);
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

    @GetMapping("/{albumId}/photos")
    public List<Photo> getPhotosFromAlbumByAlbumId(@PathVariable(name = "albumId") String albumId,
                              @RequestHeader(name = "idToken") String idToken)
            throws
            FirebaseAuthException,
            InvalidTokenException,
            IOException {
        this.createdBy = getEmailIdIfAuthenticatedUser(idToken);
        return photoService.getPhotoFromAlbum(albumId);
    }

    private String getEmailIdIfAuthenticatedUser(String idToken)
            throws IOException, FirebaseAuthException, InvalidTokenException {
        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);
        if (firebaseUser != null) {
//            System.out.println("*** Validation worked - email = "+firebaseUser.getEmail());
            return firebaseUser.getEmail();
        } else {
//            System.out.println("*** Validation did not work - throwing exception");
            throw new InvalidTokenException();
        }
    }

//    @RequestMapping(value = "/coverphoto",
//            produces = "application/json",
//            method=RequestMethod.PUT)
    @GetMapping("/coverphoto")
    public Album updateCoverPhoto(@RequestParam(name="albumId") String albumId,
                                  @RequestParam(name="photoUrl") String photoURL,
                                  @RequestHeader(name="idToken") String idToken)
            throws  FirebaseAuthException,
                    InvalidTokenException,
                    IOException {
//        System.out.println("*** Dodge this - inside put cover - albumId = "+albumId);
//        System.out.println("*** Dodge this - inside put cover - photoURL = "+photoURL);

        this.createdBy = getEmailIdIfAuthenticatedUser(idToken);
        Album album = albumService.getAlbumById(albumId);
        album.setCoverPhotoUrl(photoURL);
        return albumService.updateAlbum(album);
    }
}
