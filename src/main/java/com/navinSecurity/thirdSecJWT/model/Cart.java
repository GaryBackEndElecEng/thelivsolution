package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalAmount=0.0;
    private Double totalAmountWithTax=0.0;
    private Date created;
    private Date purchased;
    private String confirmation;
    private String summary;
    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key column in Cart table
    @JsonBackReference("cart")
    private User user;
    @OneToMany(mappedBy="cart",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference("cartItem")
    private Set<CartItem> cartItems;


    @PrePersist
    private void onCreated(){
        this.created=new Date();
    }

    public void addItem(CartItem item){
        this.cartItems.add(item);
        item.setCart(this);
        updateTotalAmount();
    };


    public void updateTotalAmount(){
//        System.out.println("BEFORE: totalAmount: " + this.totalAmount);
        this.totalAmount= this.cartItems.stream()
                .map(CartItem::getTotalPrice)
                .reduce(0.0, Double::sum);
//        System.out.println("AFTER: totalAmount: " + this.totalAmount);
    };





}




















