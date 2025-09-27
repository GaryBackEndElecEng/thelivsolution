package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.TaxModel;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.TaxModelRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import com.navinSecurity.thirdSecJWT.response.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.nio.file.NoSuchFileException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaxService implements  ITaxService {
    @Autowired
    private final TaxModelRepo taxModelRepo;
    @Autowired
    private final UserRepo userRepo;

    @Override
    public TaxModel getTaxModelByCountry(String country,String prov_state) {
        Optional<TaxModel> option=taxModelRepo.findByCountry(country).stream()
                .filter(tax->(tax.getProv_state().equals(prov_state))).findFirst();
        if(option.isPresent()){
            return option.get();
        }else{
            throw new RuntimeException("not found");
        }
    }

    @Override
    public TaxModel postTaxModel(TaxModel taxmodel,Long user_id) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){

        Optional<TaxModel> option=taxModelRepo.findByCountry(taxmodel.getCountry()).stream()
                .filter(tax->(tax.getProv_state().equals(taxmodel.getProv_state()))).findFirst();
            if(option.isEmpty()){
                return taxModelRepo.save(taxmodel);
            }
            throw new RuntimeException("already there");
        }else{
           throw new NotAuthorizedException("no user found");
        }
    }

    @Override
    public TaxModel updateTaxModel(TaxModel taxmodel,Long user_id) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            return taxModelRepo.save(taxmodel);
        }else{
            throw new NotAuthorizedException("no user");
        }
    }

    @Override
    public Long deleteTaxModel(Long taxId,Long user_id) {
        Optional<TaxModel> option=taxModelRepo.findById(taxId);
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent()){
            if(option.isPresent()){
                taxModelRepo.delete(option.get());
                return taxId;
            }else{
                NoSuchFileException file=new NoSuchFileException("no file found");
                throw new RuntimeException(file);
            }
        }else{
            throw new NotAuthorizedException("no user");
        }

    }

    @Override
    public List<TaxModel> getAllTaxModels() {
        return taxModelRepo.findAll();
    }
}























