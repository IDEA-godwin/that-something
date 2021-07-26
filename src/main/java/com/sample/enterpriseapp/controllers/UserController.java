package com.sample.enterpriseapp.controllers;

import com.sample.enterpriseapp.DTO.ResponseDTO;
import com.sample.enterpriseapp.DTO.request.LoginRequestDTO;
import com.sample.enterpriseapp.DTO.request.SignUpRequestDTO;
import com.sample.enterpriseapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@Valid @RequestBody LoginRequestDTO user) {
        return userService.loginUser(user.getUsername(), user.getPassword());
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@Valid @RequestBody SignUpRequestDTO user,
                                                @RequestParam(required = false) String role) {
        return userService.registerUser(user, role);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest req) {
        userService.logout(req);
    }

    @GetMapping("/users/me")
    public ResponseEntity<ResponseDTO> getLoggedInUser(HttpServletRequest req) {
        return userService.loggedInUser(req);
    }
}
