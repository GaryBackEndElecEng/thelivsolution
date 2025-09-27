package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Affiliate;
import com.navinSecurity.thirdSecJWT.model.Contact;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.AffilRepo;
import com.navinSecurity.thirdSecJWT.repo.ContactRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ContactService implements IContactService {

    @Autowired
    private final ContactRepo contactRepo;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final MyUserDetailsService userDetailsService;
    @Autowired
    private final AffilRepo affilRepo;

    @Override
    public List<Contact> getAllAssociates(Long user_id) {
        Optional<User>user=userRepo.findById(user_id);
        boolean check=user.isPresent() && this.isAuthorized(user.get());
        if(check){
            return contactRepo.findAll();

        }else{
            throw new RuntimeException("not Authorized");
        }
    }

    @Override
    public Contact getAssociate(Long user_id, Long contact_id) {
        Optional<User> option=userRepo.findById(user_id);
        Optional<Contact> contact=contactRepo.findById(contact_id);
        if(option.isPresent() ){
        this.isAuthorized(option.get());
            if(contact.isPresent()){
                return contact.get();
            }else{
                throw new RuntimeException("not found");
            }

        }else{
            throw new RuntimeException("not Authorized");
        }
    }


    @Override
    public Long deleteAssociate(Long user_id,Long contact_id) {
        Optional<User>user=userRepo.findById(user_id);
        Optional<Contact>contact=contactRepo.findById(contact_id);
        boolean check=user.isPresent() && this.isAuthorized(user.get());
        if(check){
            if(contact.isPresent()){
                contactRepo.delete(contact.get());
                return contact_id;
            }else{
                throw new RuntimeException("not found");
            }

        }else{
            throw new RuntimeException("not authorized");
        }

    }

    @Override
    public Contact postAssociate(Contact contact,Long user_id) {
        Optional<User>user=userRepo.findById(user_id);
        boolean check=user.isPresent() && this.isAuthorized(user.get());
        if(check){
            return insertAffiliate(contact);
        }else{
            throw new RuntimeException("not authorized");
        }
    }


    public Contact insertAffiliate(Contact contact){
        Affiliate aff=contact.getAffiliate();
        Optional<Affiliate> affiliate=affilRepo.findById(aff.getAffiliate_id());
        if(affiliate.isPresent()){
            contact.setAffiliate(affiliate.get());
             contact=contactRepo.save(contact);
            affiliate.get().getContacts().add(contact);
            affilRepo.save(affiliate.get());
            return contact;
        }else{
            return contactRepo.save(contact);

        }

    }

    @Override
    public Contact updateAssociate(Contact contact,Long user_id) {
        Optional<User>user=userRepo.findById(user_id);
        boolean check=user.isPresent() && this.isAuthorized(user.get());
        if(check){
        Optional<Contact> option=contactRepo.findById(contact.getContact_id());
            if(option.isPresent()){
               return contactRepo.save(contact);
            }else{
                throw new RuntimeException("not found");
            }
        }else{
            throw new RuntimeException("not Authorized");
        }
    }

    public boolean isAuthorized(User user){
        UserDetails userDetails=userDetailsService.loadUserByUsername(user.getEmail());
        if (userDetails != null && userDetails.getAuthorities() != null) {
            for(GrantedAuthority grantedAuthority:userDetails.getAuthorities()){
                var temp=grantedAuthority.getAuthority();
//                System.out.println("GRANTED"+ temp);//=> ROLE_ADMIN...
                boolean check=grantedAuthority.getAuthority().equals("ROLE_ADMIN")||
                        grantedAuthority.getAuthority().equals("ROLE_MANAGER");
                if(check){
                    return true;
                }
            }

        }
        return false;
    }
}























