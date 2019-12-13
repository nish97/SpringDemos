package com.stackroute.authentication.domain;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginDTO {
    private String email;
    private String password;
}
