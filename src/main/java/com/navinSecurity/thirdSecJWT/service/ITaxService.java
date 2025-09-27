package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.TaxModel;
import com.navinSecurity.thirdSecJWT.repo.TaxModelRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITaxService {

    TaxModel getTaxModelByCountry(String country,String prov_state);
    TaxModel postTaxModel(TaxModel taxmodel,Long user_id);
    TaxModel updateTaxModel(TaxModel taxmodel,Long user_id);
    Long deleteTaxModel(Long taxId,Long user_id);
    List<TaxModel> getAllTaxModels();
}
