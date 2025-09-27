package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.ProductCategory;
import com.navinSecurity.thirdSecJWT.model.ServiceCategory;

import java.util.List;

public interface IServCatService {
    List<ServiceCategory> getAllCategory();
    ServiceCategory findByName(String name);

    Long deleteCategory(Long user_id, Long categoryId);

    ServiceCategory updateCategory(ServiceCategory category);
    ServiceCategory findById(Long categoryId);
    ServiceCategory post(ServiceCategory servCat,Long user_id);
}
