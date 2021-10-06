package com.sample.enterpriseapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount = new BigDecimal(0);

    @OneToOne(fetch =  FetchType.LAZY, mappedBy = "cart")
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH},fetch = FetchType.EAGER)
    private Set<Product> products;

    public void addProduct(Product product) {
        this.amount = this.amount.add(product.getPrice());
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.amount = this.amount.subtract(product.getPrice());
        this.products.remove(product);
    }



}
