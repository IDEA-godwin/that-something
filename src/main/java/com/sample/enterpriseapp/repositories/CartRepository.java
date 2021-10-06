package com.sample.enterpriseapp.repositories;

import com.sample.enterpriseapp.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {


}
