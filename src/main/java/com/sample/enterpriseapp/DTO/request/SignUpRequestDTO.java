package com.sample.enterpriseapp.DTO.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequestDTO {

    @NotBlank(message = "full name can not be null")
    private String fullName;

    @NotBlank(message = "username is required")
    private String username;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
