package com.stackroute.authentication.domain;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UpdateDTO {
    private String email;
    private String currentPassword;
    private String newPassword;
}
