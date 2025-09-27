package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.navinSecurity.thirdSecJWT.dto.ProductDtoCreate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

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
    @Column(columnDefinition = "TEXT",length=1000)
    private String description;
    private String image;
    private Double price;
    private int qty;
    private String sku;
    private Date created;

    @ManyToOne
    @JoinColumn(name="productCategory_id")
    @JsonBackReference("product")
    private ProductCategory productCategory;

    @ManyToOne
    @JoinColumn(name="affiliate_id")
    @JsonBackReference("affiliate-product")
    private Affiliate affiliate;




    public Product(ProductDtoCreate prodDto){
        this.name=prodDto.getName();
        this.cat=prodDto.getCat();
        this.description=prodDto.getDescription();
        this.image= prodDto.getImage();
        this.price= prodDto.getPrice();
        this.qty= prodDto.getQty();
        this.sku= prodDto.getSku();
        this.setProductCategory(prodDto.getProductCategory());
    }



    public Product convert(ProductDtoCreate prod,ProductCategory prodCat){

        return Product.builder()
                .name(prod.getName())
                .cat(prodCat.getName())
                .qty(prod.getQty())
                .description(prod.getDescription())
                .image(prod.getImage())
                .price(prod.getPrice())
                .sku(prod.getSku())
                .productCategory(prodCat)
                .build();
    }
    public Product convertProd(Product prod){
        return Product.builder()
                .name(prod.getName())
                .cat(prod.getCat())
                .qty(prod.getQty())
                .description(prod.getDescription())
                .image(prod.getImage())
                .price(prod.getPrice())
                .sku(prod.getSku())
                .productCategory(prod.getProductCategory())
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
};






















