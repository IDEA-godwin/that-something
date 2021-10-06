package com.sample.enterpriseapp.services;

import com.sample.enterpriseapp.models.Category;
import com.sample.enterpriseapp.models.Product;
import com.sample.enterpriseapp.repositories.CategoryRepository;
import com.sample.enterpriseapp.repositories.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public UserProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByCategory(String categoryName) {
        if(categoryRepository.findCategoryByName(categoryName).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "invalid category name");
        }
        Category category = categoryRepository.findCategoryByName(categoryName).get();
        return productRepository.findAllByCategory(category);
    }
}
