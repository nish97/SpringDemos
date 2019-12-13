package com.stackroute.authentication.exception;

import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalException extends Exception {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> userAlreadyExistException(final UserAlreadyExistException e){
        return new ResponseEntity<>(new Gson().toJson(e.getMessage()), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(AuthorizationFailureException.class)
    public ResponseEntity<String> authorizationFailureException(final AuthorizationFailureException e){
        return new ResponseEntity<>(new Gson().toJson(e.getMessage()), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UserCreationFailureException.class)
    public ResponseEntity<String> userCreationFailureException(final UserCreationFailureException e){
        return new ResponseEntity<>(new Gson().toJson(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<String> userDoesNotExistException(final UserDoesNotExistException e){
        return new ResponseEntity<>(new Gson().toJson(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}
