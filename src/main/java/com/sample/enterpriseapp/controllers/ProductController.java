package com.sample.enterpriseapp.controllers;

import com.sample.enterpriseapp.DTO.ResponseDTO;
import com.sample.enterpriseapp.models.Product;
import com.sample.enterpriseapp.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> createProduct(@RequestBody Product product) {
        return new ResponseEntity<>(new ResponseDTO("success", productService.createProduct(product)),
                                    HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDTO> getProduct(@PathVariable Long productId) {
        try {
            return new ResponseEntity<>(new ResponseDTO("success", productService.getProduct(productId)),
                                        HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage(), null), ex.getStatus());
        }
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> editProduct(@RequestBody Product product, @PathVariable Long productId) {
        try {
            return new ResponseEntity<>(new ResponseDTO("success", productService.editProduct(productId, product)),
                                        HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage(), null), ex.getStatus());
        }
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseDTO> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(new ResponseDTO("success", null), HttpStatus.OK);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(new ResponseDTO(ex.getMessage(), null), ex.getStatus());
        }
    }

}
