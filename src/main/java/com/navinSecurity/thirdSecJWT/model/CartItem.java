package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jdk.jfr.Percentage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int prodQuantity;
    private int servQuantity;
    private Double productPrice;
    private Double servicePrice;
    private Double totalPrice;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="serviceMod_id")
    private ServiceMod serviceMod;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    @JsonBackReference
    private Cart cart;

    public CartItem(Product product,ServiceMod serviceMod){
        this.product=product;
        this.serviceMod=serviceMod;
        this.productPrice =(product.getPrice() !=null) ? product.getPrice():0.0;
        this.servicePrice=(serviceMod.getPrice() !=null) ? serviceMod.getPrice():0.0;
    }

    public void setTotalPrice(){
        this.totalPrice=this.productPrice * this.prodQuantity + this.servicePrice*this.servQuantity;
    }
}
