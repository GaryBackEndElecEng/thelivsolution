package com.navinSecurity.thirdSecJWT.dto;

import com.navinSecurity.thirdSecJWT.model.Affiliate;
import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.model.ServiceMod;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Optional;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostAffiliateDto {
    private String company;
    private String url;
    private String token;
    private String imgUrl;
    private Set<Product> products;
    private Set<ServiceMod> serviceMods;


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
