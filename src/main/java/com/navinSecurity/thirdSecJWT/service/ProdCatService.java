package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.ProductDtoCreate;
import com.navinSecurity.thirdSecJWT.model.*;
import com.navinSecurity.thirdSecJWT.repo.ProdCategoryRepo;
import com.navinSecurity.thirdSecJWT.repo.ProductRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdCatService implements IProdCatService {

    @Autowired
    private final ProdCategoryRepo prodCategoryRepo;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private ProductRepo productRepo;


    @Override
    public List<ProductCategory> getAllCategory() {
        return prodCategoryRepo.findAll();
    };

    @Override
    public ProductCategory findByName(String name) {
        Optional<ProductCategory> option=prodCategoryRepo.findByName(name);
        if(option.isPresent()){
            return option.get();
        }else{
            throw new RuntimeException("no Category found");
        }

    };

    @Override
    public Long deleteCategory(Long user_id,Long categoryId) {
        Optional<ProductCategory> option=prodCategoryRepo.findById(categoryId);
        if(isUser(user_id)){
            if(option.isPresent()){
                prodCategoryRepo.delete(option.get());
                return categoryId;
            }else{
                throw new RuntimeException("not deleted");
            }

        }else{
            throw new RuntimeException("no User Found, forbidden");
        }
    };

    @Override
    public ProductCategory updateCategory(ProductCategory category,Long user_id) {
        Optional<ProductCategory> option=prodCategoryRepo.findById(category.getId());
        if(isUser(user_id)){
            if(option.isPresent()){
                return prodCategoryRepo.save(category);
            }else{
                throw new RuntimeException(" Product Category was not updated");
            }

        }else{
            RuntimePermission notAllowed = new RuntimePermission(" not allowed");
            throw new RuntimeException(String.valueOf(notAllowed));
        }
    };

    @Override
    public ProductCategory findById(Long categoryId) {
        Optional<ProductCategory> option=prodCategoryRepo.findById(categoryId);
        if(option.isPresent()){
            return option.get();
        }else{
            throw new RuntimeException("Product Category was not found");
        }

    }

    @Override
    public ProductCategory post(ProdCatDto prodCat, Long user_id) {
        Optional<ProductCategory> option=prodCategoryRepo.findByName(prodCat.getName());
        if(isUser(user_id) && option.isEmpty()){
            ProductCategory convertProd=prodCat.prodcatDtoToProductCategory(prodCat);
            return attachNewProductsToNewCategoryAndSave(convertProd);

        }else{
            RuntimePermission notAllowed = new RuntimePermission(" not allowed");
            throw new RuntimeException(String.valueOf(notAllowed));
        }

    };

    public Boolean isUser(Long user_id){
        Optional<User> isUser=userRepo.findById(user_id);
        return isUser.isPresent();
    }

    public List<Product> setProductsToProdcat(ProductCategory prodCat,List<Product> products){
        List<Product> newProds=new ArrayList<>();
        for (Product prod : products) {
            prod.setProductCategory(prodCat);
            prod.setCat(prodCat.getName());
            ProductDtoCreate newProd = new ProductDtoCreate(prod);
            Product newerProd=new Product(newProd);
            Product retProd = productRepo.save(newerProd);
            newProds.add(retProd);
        }
        return newProds;
    }
    public ProductCategory attachNewProductsToNewCategoryAndSave(ProductCategory prodCat){
        ProductCategory newServCat=new ProductCategory(prodCat.getName());
        ProductCategory savedServCat=prodCategoryRepo.save(newServCat);
        if(!prodCat.getProducts().isEmpty()){
            for(Product product :prodCat.getProducts()){
                Product newServ=new Product();
                newServ.convertProd(product);
                Product retServ=productRepo.save(newServ);
                retServ.setProductCategory(savedServCat);
                retServ.setCat(savedServCat.getName());
                savedServCat.getProducts().add(retServ);
                productRepo.save(newServ);
            }
        }

        return prodCategoryRepo.save(savedServCat);
    }


};




























