package com.example.fireAuth_REST_API.exception;

import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler extends Exception{

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        String msg = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NameNotAllowedException.class)
    public ResponseEntity<String> NameNotAllowedError(NameNotAllowedException na){
        return new ResponseEntity<>(na.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> noSuchElementError(){
        return new ResponseEntity<>("Item could not be found", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(FirebaseAuthException.class)
    public ResponseEntity<String> firebaseAuthError(FirebaseAuthException fae){
        return new ResponseEntity<>("*** GEH: "+fae.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> invalidTokenError(InvalidTokenException ite){
        return new ResponseEntity<>("*** GEH: "+ite.getMessage(),HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public ResponseEntity<String> UserAuthError(UserNotAuthorizedException una){
        return new ResponseEntity<>(una.getMessage(),HttpStatus.FORBIDDEN);
    }
}
