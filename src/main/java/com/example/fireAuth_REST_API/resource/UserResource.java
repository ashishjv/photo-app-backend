package com.example.fireAuth_REST_API.resource;

import com.example.fireAuth_REST_API.exception.InvalidTokenException;
import com.example.fireAuth_REST_API.exception.NameNotAllowedException;
import com.example.fireAuth_REST_API.exception.UserNotAuthorizedException;
import com.example.fireAuth_REST_API.model.FirebaseUser;
import com.example.fireAuth_REST_API.model.User;
import com.example.fireAuth_REST_API.service.FirebaseService;
import com.example.fireAuth_REST_API.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private FirebaseService firebaseService;

    @GetMapping("/all")
    public List<User> getAllUsers(@RequestHeader(name = "idToken") String idToken)
            throws FirebaseAuthException,
            InvalidTokenException, IOException {
        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);
        if (firebaseUser != null) {
            return userService.getAllUsers();
        }

        return null;
    }

    @GetMapping("/id")
    public User getUserById(@RequestParam(name = "userId") String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public User getUserByIdToken(@RequestHeader(name = "idToken") String idToken)
            throws IOException,
            FirebaseAuthException,
            InvalidTokenException {
        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);
        if (firebaseUser != null) {
            return userService.getUserFromEmail(firebaseUser.getEmail());
        } else {
            return null;
        }
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody @Valid User user) {
        System.out.println("*** Registering new user = " + user.getEmail());
        System.out.println("*** Registering new user = " + user.getName());
        System.out.println("*** Registering new user = " + user.getId());
        System.out.println("*** Registering new user = " + user.getProfilePhotoUrl());
        user.setId(null);
        return this.userService.saveUser(user);
    }

    @PostMapping
    public User saveUser(@RequestBody @Valid User user,
                         @RequestHeader(name = "idToken") String idToken)
            throws NameNotAllowedException,
            IOException,
            FirebaseAuthException,
            InvalidTokenException {
        System.out.println("***** DODGE THIS: Its IN POST ******");
        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        System.out.println("***** DODGE THIS: Firebase Email is " + firebaseUser.getEmail());
        System.out.println("***** DODGE THIS: User Email is " + user.getEmail());

        System.out.println("*** DODGE THIS:  user.email = " + user.getEmail());
        System.out.println("*** DODGE THIS:  user.name = " + user.getName());
        System.out.println("*** DODGE THIS:  user.id = " + user.getId());
        System.out.println("*** DODGE THIS:  user.profilepicurl = " + user.getProfilePhotoUrl());

        if (firebaseUser != null) {
            if (user.getName().equalsIgnoreCase("root")) {
                throw new NameNotAllowedException();
            }
            user.setEmail(firebaseUser.getEmail());
            return userService.saveUser(user);
        } else {
            throw new InvalidTokenException();
        }
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user,
                           @RequestHeader(name = "idToken") String idToken)
            throws NameNotAllowedException,
            IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        if (user.getName().equalsIgnoreCase("root")) {
            throw new NameNotAllowedException();
        }

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            if (!firebaseUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new UserNotAuthorizedException(
                        "FireBaseUser.email: " + firebaseUser.getEmail() + "\n" +
                                "User.email: " + user.getEmail()
                );
            } else {
                return userService.updateUser(user);
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @DeleteMapping
    public String deleteUser(@RequestParam(name = "userId") String userId,
                             @RequestHeader(name = "idToken") String idToken)
            throws IOException,
            FirebaseAuthException,
            InvalidTokenException,
            UserNotAuthorizedException {

        User user = userService.getUserById(userId);

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            if (!firebaseUser.getEmail().equalsIgnoreCase(user.getEmail())) {
                throw new UserNotAuthorizedException(
                        "FireBaseUser.email: " + firebaseUser.getEmail() + "\n" +
                                "User.email: " + user.getEmail()
                );
            } else {
                return userService.deleteUser(userId);
            }
        } else {
            throw new InvalidTokenException();
        }
    }

    @GetMapping("/makeProfilePic")
    public User makeProfilePic(@RequestParam(name = "photoUrl") String photoUrl,
                               @RequestHeader(name = "idToken") String idToken)
            throws IOException, FirebaseAuthException, InvalidTokenException {

        FirebaseUser firebaseUser = firebaseService.authenticate(idToken);

        if (firebaseUser != null) {
            User user = userService.getUserFromEmail(firebaseUser.getEmail());
            user.setProfilePhotoUrl(photoUrl);
            return userService.updateUser(user);
        } else {
            throw new InvalidTokenException();
        }
    }
}

