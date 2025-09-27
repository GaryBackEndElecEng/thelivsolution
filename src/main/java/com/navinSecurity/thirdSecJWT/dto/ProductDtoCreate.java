package com.navinSecurity.thirdSecJWT.dto;

import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.model.ProductCategory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDtoCreate {
    private String name;
    private String description;
    private String cat;
    private int qty;
    private String image;
    private Double price;
    private String sku;
    private ProductCategory productCategory;

    public ProductDtoCreate(Product prod){
        this.name=prod.getName();
        this.description= prod.getDescription();
        this.cat= prod.getProductCategory().getName();
        this.qty= prod.getQty();
        this.image=prod.getImage();
        this.price=prod.getPrice();
        this.sku=prod.getSku();
        this.productCategory=prod.getProductCategory();
    }
    public Product convert(ProductDtoCreate prodDto){
        return Product.builder()
                .name(prodDto.getName())
                .description(prodDto.getDescription())
                .cat(prodDto.getCat())
                .qty(prodDto.getQty())
                .image(prodDto.getImage())
                .price(prodDto.getPrice())
                .sku(prodDto.getSku())
                .productCategory(prodDto.getProductCategory())
                .build();
    }
}
