package com.sample.enterpriseapp.controllers;

import com.sample.enterpriseapp.DTO.ResponseDTO;
import com.sample.enterpriseapp.services.UserProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
public class UserProductController {

    private final UserProductService userProductService;

    public UserProductController(UserProductService userProductService) {
        this.userProductService = userProductService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllProducts() {
        ResponseDTO res = new ResponseDTO("success", userProductService.getAllProducts());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("?category={category}")
    public ResponseEntity<ResponseDTO> getAllProductByCategory(@PathVariable String category) {
        try {
            ResponseDTO res = new ResponseDTO("success", userProductService.getAllProductsByCategory(category));
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage(), null), ex.getStatus());
        }
    }

}
