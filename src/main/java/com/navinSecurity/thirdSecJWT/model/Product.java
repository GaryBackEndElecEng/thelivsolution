package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.navinSecurity.thirdSecJWT.dto.ProductDtoCreate;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long prodId;
    private String name;
    private String cat;
    private String description;
    private String image;
    private Double price;
    private int qty;
    private String sku;
    private Date created;

    @ManyToOne
    @JoinColumn(name="productCategory_id")
    @JsonBackReference
    private ProductCategory productCategory;






    public Product convert(ProductDtoCreate prod){
        return Product.builder()
                .name(prod.getName())
                .cat(prod.getCat())
                .description(prod.getDesc())
                .image(prod.getImage())
                .build();
    }

    public static Product updateQty(Product product,int qty){
        product.setQty(qty);
        return product;
    }

    @PrePersist
    protected void onCreate(){
        this.created=new Date();
    }
}
