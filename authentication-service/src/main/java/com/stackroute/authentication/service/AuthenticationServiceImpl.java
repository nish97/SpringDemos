package com.stackroute.authentication.service;

import com.stackroute.authentication.domain.LoginDTO;
import com.stackroute.authentication.domain.RegisterDTO;
import com.stackroute.authentication.domain.UpdateDTO;
import com.stackroute.authentication.domain.UserDetails;
import com.stackroute.authentication.exception.*;
import com.stackroute.authentication.repository.AuthenticationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private String message  = "User with emailId:";
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public void userAuthenticator(LoginDTO loginDTO) throws UserDoesNotExistException, AuthorizationFailureException {
        doesUserExists(loginDTO.getEmail());
        doesPasswordMatch(loginDTO.getPassword(),authenticationRepository.findByEmail(loginDTO.getEmail()).getPassword(),loginDTO.getEmail(),"");
    }

    @Override
    public String saveUser(RegisterDTO registerDTO) throws UserAlreadyExistException, UserCreationFailureException {
        if(authenticationRepository.existsUserDetailsByEmail(registerDTO.getEmail())) {
            log.error(message+registerDTO.getEmail()+" already exists");
            throw new UserAlreadyExistException("User already exists");
        }
        else {
            authenticationRepository.saveAndFlush(new UserDetails(registerDTO.getEmail(),passwordEncoder.encode(registerDTO.getPassword())));
            if(authenticationRepository.existsUserDetailsByEmail(registerDTO.getEmail())) {
                log.info(message+registerDTO.getEmail()+" is saved successfully");
                return "User Saved";
            }
            else {
                log.error(message+registerDTO.getEmail()+" cannot be saved");
                throw new UserCreationFailureException("Was not able to Register User with emailId:"+registerDTO.getEmail()+" try Registering again");
            }
        }
    }

    @Override
    public String deleteUser(LoginDTO loginDTO) throws UserDoesNotExistException, AuthorizationFailureException {
        doesUserExists(loginDTO.getEmail());
        UserDetails userDetails = authenticationRepository.findByEmail(loginDTO.getEmail());
        doesPasswordMatch(loginDTO.getPassword(),userDetails.getPassword(),loginDTO.getEmail(),"delete ");
        authenticationRepository.delete(userDetails);
        log.info(message + loginDTO.getEmail() + " is successfully deleted");
        return "User deleted successfully";
    }

    @Override
    public String updateUserPassword(UpdateDTO updateDTO) throws UserDoesNotExistException, AuthorizationFailureException {
        doesUserExists(updateDTO.getEmail());
        UserDetails userDetails = authenticationRepository.findByEmail(updateDTO.getEmail());
        doesPasswordMatch(updateDTO.getCurrentPassword(),userDetails.getPassword(),updateDTO.getEmail(),"update ");
        userDetails.setPassword(updateDTO.getNewPassword());
        authenticationRepository.saveAndFlush(userDetails);
        log.info(message + updateDTO.getEmail() + " is successfully updated");
        return "User password updated successfully";
    }

    @Override
    public UserDetails getUserDetails(LoginDTO loginDTO) throws UserDoesNotExistException {
        doesUserExists(loginDTO.getEmail());
        return authenticationRepository.findByEmail(loginDTO.getEmail());
    }

    @Override
    public void doesUserExists(String email) throws UserDoesNotExistException {
        if(!authenticationRepository.existsUserDetailsByEmail(email)){
            log.error(message+email+" Does not exist");
            throw new UserDoesNotExistException("User does not exist");
        }
    }

    @Override
    public void doesPasswordMatch(String password,String encodedPassword,String email,String from) throws AuthorizationFailureException {
        if(!new BCryptPasswordEncoder().matches(password,encodedPassword)) {
            log.error(message+email+" "+from+"authorization unsuccessful");
            throw new AuthorizationFailureException("User authorization unsuccessful try again with correct password");
        }
    }
}
