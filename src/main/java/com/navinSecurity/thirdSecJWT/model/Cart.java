package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalAmount=0.0;
    @OneToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id") // Foreign key column in Cart table
    private User user;
    @OneToMany(mappedBy="cart",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems;

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

    public void removeCardItem(CartItem cartItem){
        this.cartItems.stream().filter(_cartItem->_cartItem.getId().equals(cartItem.getId()))
                .findFirst()
                .ifPresent(_cartItem->this.cartItems.remove(_cartItem));
    }

    public void removeProdServItem(CartItem item){
        this.cartItems.stream()
                .filter(_item -> _item.getId().equals(item.getId()))
                .findFirst().ifPresent(cartItem -> this.cartItems.remove(item));
    };
    public void removeProdItem(Product prod){
        this.cartItems.stream()
                .filter(_item->_item.getProduct().equals(prod))
                .findFirst().ifPresent(cartItem->{
                    cartItem.setProduct(null);
                });
    };
    public void removeServItem(ServiceMod servItem){
        this.cartItems.stream()
                .filter(_item->_item.getServiceMod().getId().equals(servItem.getId()))
                .findFirst().ifPresent(cartItem->{
                    cartItem.setServiceMod(null);
                });
    }
    public void addServItem(ServiceMod servItem){
        this.cartItems.stream()
                .filter(cartItem->cartItem.getServiceMod().getId().equals(servItem.getId()))
                .findFirst().ifPresent(cartItem->cartItem.setServiceMod(servItem));
    }

    public void addProdItem(Product product){
        this.cartItems.stream()
                .filter(cartItem->cartItem.getProduct().getProdId().equals(product.getProdId()))
                .findFirst()
                .ifPresent(cartItem->cartItem.setProduct(product));
    }
}




















