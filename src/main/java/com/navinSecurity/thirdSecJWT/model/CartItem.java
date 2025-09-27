package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jdk.jfr.Percentage;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int prodQuantity=0;
    private int servQuantity=0;
    private Double productPrice=(double) 0;
    private Double servicePrice=(double) 0;
    private Double totalPrice= (double) 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="serviceMod_id")
    private ServiceMod serviceMod;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="cart_id")
    @JsonBackReference("cartItem")
    private Cart cart;

    public CartItem(Product product,ServiceMod serviceMod){

        if(product !=null){
            this.product=product;
            this.productPrice =(product.getPrice() !=null) ? product.getPrice():0.0;
            this.prodQuantity=this.prodQuantity + 1;
        }else if(serviceMod !=null){
            this.serviceMod=serviceMod;
            this.servicePrice=(serviceMod.getPrice() !=null) ? serviceMod.getPrice():0.0;
            this.servQuantity=this.servQuantity + 1;
        }
    }

    public void setTotalPrice(){
        if(this.product !=null){
            this.totalPrice=this.productPrice * this.prodQuantity;
        }else if(this.serviceMod !=null){
            this.totalPrice=this.servicePrice*this.servQuantity;
        }
    }
}
