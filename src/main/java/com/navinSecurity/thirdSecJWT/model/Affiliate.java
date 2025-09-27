package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.navinSecurity.thirdSecJWT.dto.PostAffiliateDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Affiliate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long affiliate_id;
    private String company;
    private String url;
    private String token;
    private String imgUrl;
    @OneToMany(mappedBy="affiliate",cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference("affiliate-product")
    private Set<Product> products;
    @OneToMany(mappedBy="affiliate",cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference("affiliate-serviceMod")
    private Set<ServiceMod> serviceMods;
    @JsonManagedReference("affiliate-contact")
    @OneToMany(mappedBy = "affiliate",cascade=CascadeType.ALL,orphanRemoval = true)
    private Set<Contact> contacts;
    @OneToOne
    @JsonManagedReference("affiliate-user")
    @JoinColumn(name = "user_id")
    private User user;


    public Affiliate(String co,String token,String imgUrl,String url,Set<Product> products,Set<ServiceMod> serviceMods){
        this.company=co;
        this.token=token;
        this.imgUrl=imgUrl;
        this.url=url;
        this.products=products;
        this.serviceMods=serviceMods;
    }

    public Affiliate postConvert(PostAffiliateDto affiliate){
        return Affiliate.builder()
                .company(affiliate.getCompany())
                .token(affiliate.getToken())
                .imgUrl(affiliate.getImgUrl())
                .url(affiliate.getUrl())
                .products(affiliate.getProducts())
                .serviceMods(affiliate.getServiceMods())
                .build();
    }
}
