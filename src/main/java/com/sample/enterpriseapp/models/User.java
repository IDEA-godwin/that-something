
package com.sample.enterpriseapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@NoArgsConstructor
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    @NotBlank(message = "full name can not be null")
    private String fullName;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "username is required")
    private String username;

    @Column(name = "email", nullable = false)
    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "password is required")
    private String password;

    @Column(name = "token")
    private String token;

    @ElementCollection(fetch = FetchType.EAGER)
    Set<Roles> roles;

    public User(String fullName, String username, String email) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
    }
}
