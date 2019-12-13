package com.stackroute.authentication.service;


import com.stackroute.authentication.domain.LoginDTO;
import com.stackroute.authentication.domain.RegisterDTO;
import com.stackroute.authentication.domain.UpdateDTO;
import com.stackroute.authentication.domain.UserDetails;
import com.stackroute.authentication.exception.*;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    String saveUser(RegisterDTO registerDTO) throws UserAlreadyExistException, UserCreationFailureException;
    String deleteUser(LoginDTO loginDTO) throws UserDoesNotExistException, AuthorizationFailureException;
    String updateUserPassword(UpdateDTO updateDTO) throws UserDoesNotExistException, AuthorizationFailureException;
    UserDetails getUserDetails(LoginDTO loginDTO) throws UserDoesNotExistException;
    void doesUserExists(String email) throws UserDoesNotExistException;
    void doesPasswordMatch(String password,String encodedPassword,String email,String from) throws AuthorizationFailureException;
    void userAuthenticator(LoginDTO loginDTO) throws UserDoesNotExistException, AuthorizationFailureException;
}
