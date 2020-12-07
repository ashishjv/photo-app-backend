package com.example.fireAuth_REST_API.service;

import com.example.fireAuth_REST_API.model.FirebaseUser;
import com.example.fireAuth_REST_API.config.FirebaseConfig;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import java.io.IOException;

@Service
public class FirebaseService {

    @Autowired
    private FirebaseConfig firebaseConfig;

    public FirebaseUser authenticate(String idToken) throws IOException, FirebaseAuthException {

        FirebaseApp firebaseApp = firebaseConfig.initializeFirebase();
        FirebaseToken firebaseToken = FirebaseAuth.getInstance(firebaseApp).verifyIdToken(idToken);

        String uid = firebaseToken.getUid();
        String name = firebaseToken.getName();
        String email = firebaseToken.getEmail();

        return new FirebaseUser(uid,name,email);
    }
}

