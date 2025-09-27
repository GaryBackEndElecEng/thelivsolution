package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Affiliate;
import com.navinSecurity.thirdSecJWT.model.Role;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.AffilRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import com.navinSecurity.thirdSecJWT.ultils.SecurityUltils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AffiliateService  implements IAffilateService{

    @Autowired
    AffilRepo affilRepo;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final SecurityUltils securityUltils;


    @Override
    public List<Affiliate> getallAffiliates(Long user_id) {
        Optional<User> isUser=userRepo.findById(user_id);
        if(isUser.isPresent()){
            boolean check=securityUltils.hasAuthority(isUser.get().getEmail());
            if(check){
                 return affilRepo.findAll();

            }else{
                throw new AuthorizationDeniedException("Not Admin or Manager");
            }
        }else{
            throw new RuntimeException("user not found");
        }
    }



    @Override
    public Affiliate findAffiliateByCo(String co) {
        Optional<Affiliate> option=affilRepo.findAll()
                .stream().filter(aff->(aff.getCompany().equals(co))).findFirst();
        if(option.isPresent()) {
            return option.get();
        }else{
            throw new RuntimeException("not found");
        }
    }

    @Override
    public Affiliate updateAffiliate(Affiliate affiliate,Long user_id) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent() && this.isManager(user.get())){
            affiliate= setProdServ(affiliate);
            return affilRepo.save(affiliate);
        }else{
            throw new AuthorizationDeniedException("not authorized");
        }
    }

    @Override
    public Long deleteAffiliate(Long affiliate_id,Long user_id) {
        Optional<User> user=userRepo.findById(user_id);
        if(user.isPresent() && this.isManager(user.get())){
            Optional<Affiliate> option=affilRepo.findById(affiliate_id);
            if(option.isPresent()) {
                affilRepo.delete(option.get());
                return affiliate_id;
            }else{
                throw new RuntimeException("not deleted");
            }

        }else{
            throw new AuthorizationDeniedException(" not authorized");
        }
    }

    @Override
    public Affiliate postAffiliate(Affiliate affiliate,Long user_id) {
        Optional<User> user=userRepo.findById(user_id);
        System.out.println("OUTSIDE:" + user.isPresent() + ": IS MANAGER: " + this.isManager(user.get()));
        if(this.isManager(user.get())){
        return affilRepo.save(affiliate);//verify that is picks up products

        }else{
            throw new AuthorizationDeniedException("not authorized");
        }
    }

    @Override
    public Affiliate findAffiliateById(Long affiliateId) {
        Optional<Affiliate> option=affilRepo.findById(affiliateId);
        if(option.isPresent()){
            return option.get();
        }else{
            throw new RuntimeException("not found");
        }

    }

    public Affiliate setProdServ(Affiliate affiliate) {
       Affiliate retAffiliate=affilRepo.save(affiliate);
        Affiliate finalRetAffiliate = retAffiliate;
        retAffiliate=(Affiliate) retAffiliate.getProducts().stream().filter(prod-> {
            prod.setAffiliate(finalRetAffiliate);
            return false;
        });
        Affiliate finalRetAffiliate1 = retAffiliate;
        retAffiliate= (Affiliate) retAffiliate.getServiceMods().stream().filter(serv->{
            serv.setAffiliate(finalRetAffiliate1);
            return false;
        });
        return retAffiliate;

    }

    public boolean isManager(User user){
        Role role=user.getRole();
        return role.name().equals("ROLE_ADMIN") || role.name().equals("ROLE_MANAGER");
    }
}






