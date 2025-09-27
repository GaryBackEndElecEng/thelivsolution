package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.ProdCatDto;
import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.model.ProductCategory;

import java.util.List;

public interface IProdCatService {
    List<ProductCategory> getAllCategory();
    ProductCategory findByName(String name);
    Long deleteCategory(Long user_id,Long categoryId);
    ProductCategory updateCategory(ProductCategory category,Long user_id);
    ProductCategory findById(Long categoryId);
    ProductCategory post(ProdCatDto prodCat, Long user_id);
}
