package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.*;

public interface ICartService {

    Cart getCart(Long user_id);
    Cart addProduct(Long user_id, Product prod);
    Cart addService(Long user_id,ServiceMod serv);
    Cart deleteItem(Long user_id,CartItem cartItem);
    Cart deleteProduct(Long user_id,Product product);
    Cart deleteService(Long user_id,ServiceMod serviceMod);
}
