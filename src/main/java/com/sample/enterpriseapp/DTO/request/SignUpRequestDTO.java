package com.sample.enterpriseapp.DTO.request;

import lombok.Data;

@Data
public class SignUpRequestDTO {

    private String fullName;
    private String username;
    private String email;
    private String password;
}
