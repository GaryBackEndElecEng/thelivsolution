package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.*;
import com.navinSecurity.thirdSecJWT.repo.CartItemRepo;
import com.navinSecurity.thirdSecJWT.repo.CartRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService  implements ICartService{

    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartItemRepo cartItemRepo;

    @Override
    public Cart getCart(Long user_id) {
        Optional<User> optionUser=userRepo.findById(user_id);
        if(optionUser.isPresent()){
            return optionUser.get().getCart();
        }else{
            throw new RuntimeException("no user && cart");
        }

    };

    @Override
    public Cart addProduct(Long user_id,Product prod) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            Cart cart=user.get().getCart();
            cart.addProdItem(prod);
            return cartRepo.save(cart);

        }else{
            throw new RuntimeException("no user");
        }

    };

    @Override
    public Cart addService(Long user_id,ServiceMod serv) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            Cart cart=user.get().getCart();
            cart.addServItem(serv);
            return cartRepo.save(cart);

        }else{
            throw new RuntimeException("no user");
        }
    };

    @Override
    public Cart deleteItem(Long user_id,CartItem cartItem) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            Cart cart=user.get().getCart();
            cart.removeCardItem(cartItem);
            return cartRepo.save(cart);

        }else{
            throw new RuntimeException("no user");
        }
    };

    @Override
    public Cart deleteProduct(Long user_id, Product product) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            Cart cart=user.get().getCart();
            cart.removeProdItem(product);
            return cartRepo.save(cart);
        }else{
            throw new RuntimeException("no user");
        }
    };

    @Override
    public Cart deleteService(Long user_id, ServiceMod serviceMod) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            Cart cart=user.get().getCart();
            cart.removeServItem(serviceMod);
            return cartRepo.save(cart);

        }else{
            throw new RuntimeException("no user");
        }
    };
}
