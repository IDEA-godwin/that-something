package com.sample.enterpriseapp.services;

import com.sample.enterpriseapp.models.Cart;
import com.sample.enterpriseapp.models.Product;
import com.sample.enterpriseapp.repositories.CartRepository;
import com.sample.enterpriseapp.repositories.UserRepository;
import com.sample.enterpriseapp.securities.JwtTokenProvider;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }

    public Cart addToCart(HttpServletRequest req, Product product) {
        String username = getUsername(req);
        Cart cart = userRepository.findByUsername(username).get().getCart();
        cart.addProduct(product);
        return cartRepository.save(cart);
    }

    public Cart removeFromCart(HttpServletRequest req, Product product) {
        String username = getUsername(req);
        Cart cart = userRepository.findByUsername(username).get().getCart();
        cart.removeProduct(product);
        return cartRepository.save(cart);
    }

    private String getUsername(HttpServletRequest req) {
        return new JwtTokenProvider().getUsername(new JwtTokenProvider().resolveToken(req));
    }

}
