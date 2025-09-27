package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.ProductDtoCreate;
import com.navinSecurity.thirdSecJWT.model.*;
import com.navinSecurity.thirdSecJWT.repo.ProdCategoryRepo;
import com.navinSecurity.thirdSecJWT.repo.ProductRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    @Autowired
    private final ProductRepo productRepo;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final ProdCategoryRepo prodCategoryRepo;

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
    public Product saveProduct(ProductDtoCreate product,Long prodCatId,Long user_id) {

        Optional<ProductCategory> productCategory=prodCategoryRepo.findById(prodCatId);
        //ADD ISADMIN ONCE USER REGISTRATION IS CONFIRMED BY EMAIL ( ENTERED BY ADMIN) IS RESOLVED
        if(isUser(user_id)){
            if(productCategory.isPresent()){
                ProductCategory newProdCat=productCategory.get();
                Product newProduct=new Product().convert(product,newProdCat);
                newProduct.setProductCategory(newProdCat);
                System.out.println("newProduct.getDescription():"+newProduct.getDescription());
                return productRepo.save(newProduct);
            }else{
                throw new RuntimeException("no Product category");
            }
        }else{
            throw new RuntimeException("No User present");
        }
    }

    @Override
    public Product updateProduct(Product product,Long user_id) {
        System.out.println("PRODUCT ID: " + product.getProdId());
        Optional<Product> found=productRepo.findById(product.getProdId());
        if(isUser(user_id)){
            if(found.isPresent()){
            System.out.println("PRODUCT FOUND: " + found.get().toString());
                 return productRepo.save(product);

            }else{
                throw new RuntimeException(" no product found");
            }

        }else{
            throw new AuthorizationDeniedException("not Admin user");
        }
    }

    @Override
    public String deleteProduct(Long prodId, Long userId) {
        Optional<Product>option=productRepo.findById(prodId);
        if(isUser(userId)){
            if(option.isPresent()){
                productRepo.delete(option.get());
                return "deleted: " + option.get().getName();
            }else{
                throw new NoSuchElementException("not found:" +prodId);
            }

        }else{
            AuthenticationNotSupportedException notAuth=new AuthenticationNotSupportedException(" not authorized");
            throw new RuntimeException(notAuth);
        }
    }

    public boolean isAdmin(Long user_id){
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            Role role=user.get().getRole();
            return role.name().equals("ADMIN");//CHECK ALTERNATE ADMIN USER
        }else{
            throw new RuntimeException("no user assigned");
        }
    };
    public boolean isUser(Long user_id){
        Optional<User> isUser=userRepo.findById(user_id);
        return isUser.isPresent();
    }







};




























