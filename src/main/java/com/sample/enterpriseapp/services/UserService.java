package com.sample.enterpriseapp.services;

import com.sample.enterpriseapp.DTO.ResponseDTO;
import com.sample.enterpriseapp.DTO.request.SignUpRequestDTO;
import com.sample.enterpriseapp.models.Roles;
import com.sample.enterpriseapp.models.User;
import com.sample.enterpriseapp.repositories.UserRepository;
import com.sample.enterpriseapp.securities.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository repository;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, AuthenticationManager authManager,
                       JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.repository = repository;
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<ResponseDTO> registerUser(SignUpRequestDTO user, String role) {
        User signUpUser = new User(user.getUsername(), user.getEmail(), user.getEmail());
        signUpUser.setPassword(passwordEncoder.encode(user.getPassword()));
        signUpUser.setRoles(createSetOfRoles(role, new HashSet<>()));
        return new ResponseEntity<>(new ResponseDTO("success", repository.save(signUpUser)), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDTO> loginUser(String username, String password) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = repository.findByUsername(username).get();
            user.setToken(jwtTokenProvider.createToken(username, user.getRoles()));
            return new ResponseEntity<>(new ResponseDTO("success", repository.save(user)), HttpStatus.OK);
        } catch (UsernameNotFoundException ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage(), new HashSet<>()), HttpStatus.NOT_FOUND);
        }
    }

    public void logout(HttpServletRequest req) {
        User user = repository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))).get();
        user.setToken("");
        repository.save(user);
    }

    public ResponseEntity<ResponseDTO> loggedInUser(HttpServletRequest req) {
        User user = repository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req))).get();
        return new ResponseEntity<>(new ResponseDTO("success", user), HttpStatus.OK);
    }

    Set<Roles> createSetOfRoles(String role, Set<Roles> roles) {
        if(role.isBlank()){
            roles.add(Roles.valueOf("USER"));
            return roles;
        }
        roles.add(Roles.valueOf(role.toUpperCase()));
        return roles;
    }
}
