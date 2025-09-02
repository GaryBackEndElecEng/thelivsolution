package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.model.Role;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.ProductRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    @Autowired
    private final ProductRepo productRepo;
    @Autowired
    private final UserRepo userRepo;

    @Override
    public List<Product> findByCat(String cat) {
        return productRepo.findByCat(cat);
    }

    @Override
    public Product findProduct(String name)  {
        Optional<Product> found=productRepo.findByName(name);
        if(found.isPresent()){
            return found.get();
        }else{
            throw new RuntimeException("product not found");
        }
    };

    @Override
    public Product getProduct(Long prodId) {
        Optional<Product> found=productRepo.findById(prodId);
        if(found.isPresent()){
            return found.get();
        }else{
            throw new RuntimeException("product not found");
        }
    };

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Product product,Long user_id) {
        Optional<Product> found=productRepo.findById(product.getProdId());
        if(found.isPresent()){
            if(this.isAdmin(user_id)){
             return productRepo.save(product);
            }else{
                return this.updateUserProduct(user_id,product);
            }
        }else{
            throw new RuntimeException(" no product found");
        }
    };

    public boolean isAdmin(Long user_id){
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            Role role=user.get().getRole();
            return role.name().equals("ADMIN");//CHECK ALTERNATE ADMIN USER
        }else{
            throw new RuntimeException("no user assigned");
        }
    };

    public Product updateUserProduct(Long user_id,Product product){
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            return Product.updateQty(product,product.getQty());
        }else{
            throw new RuntimeException("no user assigned");
        }
    };



};




























