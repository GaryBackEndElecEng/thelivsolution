package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.Charge;
import com.navinSecurity.thirdSecJWT.model.*;

import java.util.List;

public interface ICartService {

    List<Cart> getUserCarts(Long user_id);
    Cart getCart(Long user_id,Long cartId);
    Cart addProduct(Long cartId, Long ProdId);
    Cart addService(Long cartId,Long servId);
    Cart deleteItem(Long cartId,Long cartItemId);
    Cart deleteProduct(Long cartId,Long prodId);
    Cart deleteService(Long cartId,Long servId);
    Cart addProductQuantity(Long cartId,Long prodId);
    Cart addServiceQuantity(Long cartId,Long servId);
    Cart subServiceQuantity(Long cartId,Long id);
    Cart subProductQuantity(Long cartId,Long id);
    Cart getSelectCart(Long userId, Long cartId);
    Charge prePurchase(Long user_id, Long cartId);
    Charge purchase(Long user_id, Long cartId);


}
