package com.stackroute.authentication.controller;

import com.google.gson.Gson;
import com.stackroute.authentication.config.JwtTokenUtil;
import com.stackroute.authentication.domain.LoginDTO;
import com.stackroute.authentication.domain.RegisterDTO;
import com.stackroute.authentication.domain.UpdateDTO;
import com.stackroute.authentication.domain.UserDetails;
import com.stackroute.authentication.exception.AuthorizationFailureException;
import com.stackroute.authentication.exception.UserAlreadyExistException;
import com.stackroute.authentication.exception.UserCreationFailureException;
import com.stackroute.authentication.exception.UserDoesNotExistException;
import com.stackroute.authentication.service.AuthenticationService;
import com.stackroute.authentication.config.AesCipher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private JwtTokenUtil jwtTokenUtil;
    private AesCipher aesCipher;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, JwtTokenUtil jwtTokenUtil, AesCipher aesCipher) {
        this.authenticationService = authenticationService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.aesCipher = aesCipher;
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) throws UserCreationFailureException, UserAlreadyExistException {
        registerDTO.setPassword(aesCipher.decrypt(registerDTO.getPassword()));
        log.info(registerDTO.toString());
        return new ResponseEntity<>(new Gson().toJson(authenticationService.saveUser(registerDTO)),HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) throws AuthorizationFailureException, UserDoesNotExistException {
        loginDTO.setPassword(aesCipher.decrypt(loginDTO.getPassword()));
        log.info(loginDTO.toString());
        authenticationService.userAuthenticator(loginDTO);
        return new ResponseEntity<>(new Gson().toJson(jwtTokenUtil.generateToken(loginDTO)),HttpStatus.ACCEPTED);
    }
    @PostMapping("/update")
    public ResponseEntity<String> updatePassword(@RequestBody UpdateDTO updateDTO) throws AuthorizationFailureException, UserDoesNotExistException {
        updateDTO.setCurrentPassword(aesCipher.decrypt(updateDTO.getCurrentPassword()));
        log.info(updateDTO.toString());
        updateDTO.setNewPassword(aesCipher.decrypt(updateDTO.getNewPassword()));
        return new ResponseEntity<>(new Gson().toJson(authenticationService.updateUserPassword(updateDTO)),HttpStatus.ACCEPTED);
    }

    @PostMapping("/test")
    public ResponseEntity<UserDetails> tokenTest(@RequestBody LoginDTO loginDTO) throws UserDoesNotExistException {
        return new ResponseEntity<>(authenticationService.getUserDetails(loginDTO),HttpStatus.ACCEPTED);
    }


}
