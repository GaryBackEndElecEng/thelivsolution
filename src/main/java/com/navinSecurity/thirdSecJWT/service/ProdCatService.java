package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.model.ProductCategory;
import com.navinSecurity.thirdSecJWT.repo.ProdCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdCatService implements IProdCatService {

    @Autowired
    private final ProdCategoryRepo prodCategoryRepo;


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
    public String deleteCategory(Long categoryId) {
        Optional<ProductCategory> option=prodCategoryRepo.findById(categoryId);
        if(option.isPresent()){
            prodCategoryRepo.delete(option.get());
            return "productCategory: " + categoryId;
        }else{
            return " product category was not deleted";
        }
    };

    @Override
    public ProductCategory updateCategory(ProductCategory category) {
        Optional<ProductCategory> option=prodCategoryRepo.findById(category.getId());
        if(option.isPresent()){
            return prodCategoryRepo.save(category);
        }else{
            throw new RuntimeException(" Product Category was not updated");
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
    public ProductCategory post(ProductCategory prodCat) {
        List<Product>posts=new ArrayList<>();
        final boolean isPost_added = posts.addAll(prodCat.getProducts());
        if (!isPost_added) {
            prodCat.setProducts(posts);
        }
        return prodCategoryRepo.save(prodCat);

    };


};




























