package com.sample.enterpriseapp.services;

import com.sample.enterpriseapp.models.Product;
import com.sample.enterpriseapp.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Long id) {
        if (productRepository.findById(id).isPresent()) {
            return productRepository.getOne(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    public Product editProduct(Long id, Product productReq) {
        if (productRepository.findById(id).isPresent()) {
            Product product = productRepository.getOne(id);
            product.setName(productReq.getName());
            product.setDescription(productReq.getDescription());
            product.setPrice(productReq.getPrice());
            product.setCategory(productReq.getCategory());
            return productRepository.save(product);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

    public void deleteProduct(Long id) {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
    }

}
