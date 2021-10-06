package com.sample.enterpriseapp.repositories;

import com.sample.enterpriseapp.models.Category;
import com.sample.enterpriseapp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);

}
