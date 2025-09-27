package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Affiliate;

import java.util.List;
import java.util.Optional;

public interface IAffilateService {
    List<Affiliate> getallAffiliates(Long user_id);
    Affiliate findAffiliateByCo(String co);
    Affiliate updateAffiliate(Affiliate affiliate,Long user_id);
    Long deleteAffiliate(Long affiliate_id,Long user_id);
    Affiliate postAffiliate(Affiliate affiliate,Long user_id);

    Affiliate findAffiliateById(Long affiliateId);
}
