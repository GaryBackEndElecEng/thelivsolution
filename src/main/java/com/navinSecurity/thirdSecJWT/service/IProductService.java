package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.ProductDtoCreate;
import com.navinSecurity.thirdSecJWT.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> findByCat(String cat);
    Product findProduct(String name);
    Product getProduct(Long prodId);
    List<Product> getAllProducts();
    Product saveProduct(ProductDtoCreate productDto,Long prodcatId,Long user_id);

    Product updateProduct(Product product,Long user_id);

    String deleteProduct(Long prodId, Long userId);
}
