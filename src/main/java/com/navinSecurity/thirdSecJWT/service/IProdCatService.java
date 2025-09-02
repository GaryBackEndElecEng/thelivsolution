package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.ProductCategory;

import java.util.List;

public interface IProdCatService {
    List<ProductCategory> getAllCategory();
    ProductCategory findByName(String name);
    String deleteCategory(Long categoryId);
    ProductCategory updateCategory(ProductCategory category);
    ProductCategory findById(Long categoryId);
    ProductCategory post(ProductCategory prodCat);
}
