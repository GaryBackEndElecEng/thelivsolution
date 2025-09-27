package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="productCategories")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy="productCategory",cascade=CascadeType.ALL,orphanRemoval = false)
    @JsonManagedReference("product")
    private List<Product> products;

    public ProductCategory(ProductCategory prodCat){
        this.name=prodCat.getName();
        this.id= prodCat.getId();
        this.products=prodCat.getProducts();
    }
    public ProductCategory(ProdCatDto prodCat){
        this.name=prodCat.getName();
        this.products=prodCat.getProducts();
    }
    public ProductCategory(String prodCatName){
        this.name=prodCatName;
        this.products=new ArrayList<Product>();
    }

}
