package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.*;
import com.navinSecurity.thirdSecJWT.repo.ServCategoryRepo;
import com.navinSecurity.thirdSecJWT.repo.ServiceRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServCatService  implements IServCatService{

    @Autowired
    private final ServCategoryRepo servCategoryRepo;
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final ServiceRepo serviceRepo;

    @Override
    public List<ServiceCategory> getAllCategory() {
        return servCategoryRepo.findAll();
    }

    @Override
    public ServiceCategory findByName(String name) {
        Optional<ServiceCategory> option=servCategoryRepo.findByName(name);
        if(option.isPresent()){
            return option.get();
        }else{
            throw new RuntimeException("service Category not there");
        }

    }


    @Override
    public Long deleteCategory(Long user_id, Long categoryId) {
        if(isUser(user_id)){
            Optional<ServiceCategory> option=servCategoryRepo.findById(categoryId);
            option.ifPresent(servCategoryRepo::delete);
            return categoryId;

        }else{
            UsernameNotFoundException except=new UsernameNotFoundException("not found");
            throw new RuntimeException(except);
        }
    }

    @Override
    public ServiceCategory updateCategory(ServiceCategory category) {
        return servCategoryRepo.save(category);
    }

    @Override
    public ServiceCategory findById(Long categoryId) {
        Optional<ServiceCategory> option=servCategoryRepo.findById(categoryId);
        if(option.isPresent()){
            return option.get();
        }else{
            throw new RuntimeException(" service category not found");
        }

    }
    @Override
    public ServiceCategory post(ServiceCategory servCat,Long user_id) {

        if(isUser(user_id)){
            return attachNewServicesToNewCategoryAndSave(servCat);

        }else{
            RuntimePermission newError = new RuntimePermission("not permitted");
            throw new RuntimeException(String.valueOf(newError));
        }

    };

    public Boolean isUser(Long user_id){
        Optional<User> isUser=userRepo.findById(user_id);
        return isUser.isPresent();
    }

    public ServiceCategory attachNewServicesToNewCategoryAndSave(ServiceCategory servCat){

        ServiceCategory newServCat=new ServiceCategory(servCat.getName());
        ServiceCategory savedServCat=servCategoryRepo.save(newServCat);
        if(!servCat.getServices().isEmpty()){
            for(ServiceMod service :servCat.getServices()){
                ServiceMod newServ=new ServiceMod();
                newServ.convertServMod(service);
                ServiceMod retServ=serviceRepo.save(newServ);
                retServ.setServiceCategory(savedServCat);
                retServ.setCat(savedServCat.getName());
                savedServCat.getServices().add(retServ);
                serviceRepo.save(newServ);
            }
        }

        return servCategoryRepo.save(savedServCat);
    }


};

































