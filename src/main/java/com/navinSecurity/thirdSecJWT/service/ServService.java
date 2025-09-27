package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.ServiceDtoCreate;
import com.navinSecurity.thirdSecJWT.model.ServiceCategory;
import com.navinSecurity.thirdSecJWT.model.ServiceMod;
import com.navinSecurity.thirdSecJWT.model.User;
import com.navinSecurity.thirdSecJWT.repo.ServCategoryRepo;
import com.navinSecurity.thirdSecJWT.repo.ServiceRepo;
import com.navinSecurity.thirdSecJWT.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.AuthenticationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServService implements IServService {

    @Autowired
    private ServiceRepo serviceRepo;
    @Autowired
    private final ServCategoryRepo servCategoryRepo;
    @Autowired
    private final UserRepo userRepo;


    @Override
    public List<ServiceMod> findByCat(String cat) {
        return serviceRepo.findByCat(cat);
    }

    @Override
    public ServiceMod findService(String name) {
        Optional<ServiceMod> serv=serviceRepo.findByName(name);
        if(serv.isPresent()){
            return serv.get();
        }else{
            throw new RuntimeException("service not found");
        }

    }

    @Override
    public ServiceMod getService(Long servId) {
        Optional<ServiceMod> serv=serviceRepo.findById(servId);
        if(serv.isPresent()){
            return serv.get();
        }else{
            throw new RuntimeException("service not found");
        }
    }

    @Override
    public List<ServiceMod> getAllServices() {
        return serviceRepo.findAll();
    }

    @Override
    public ServiceMod saveService(ServiceDtoCreate serviceDto, Long user_id, Long id) {
        ServiceCategory servCat=getServCat(serviceDto.getCat(),id);
        ServiceMod service=new ServiceMod().convert(serviceDto,servCat);
        if(isUser(user_id)){
            return serviceRepo.save(service);

        }else{
            IllegalAccessException noUser = new IllegalAccessException("no user assigned");
            throw new RuntimeException(noUser);
        }
    }

    @Override
    public ServiceMod updateService(ServiceMod service,Long user_id) {
        if(isUser(user_id)){
            return serviceRepo.save(service);

        }else{
            IllegalAccessException noUser = new IllegalAccessException("no user assigned");
            throw new RuntimeException(noUser);
        }
    }

    @Override
    public String deleteService(Long servId, Long userId) {
        Optional<ServiceMod>option=serviceRepo.findById(servId);
        if(isUser(userId)){
            if(option.isPresent()){
                serviceRepo.delete(option.get());
                return "deleted: " + option.get().getName();
            }else{
                throw new NoSuchElementException("not found:" +servId);
            }

        }else{
            AuthenticationNotSupportedException notAuth=new AuthenticationNotSupportedException(" not authorized");
            throw new RuntimeException(notAuth);
        }

    }


    public Boolean isUser(Long user_id){
        Optional<User> isUser=userRepo.findById(user_id);
        return isUser.isPresent();
    }

    public ServiceCategory getServCat(String cat,Long id){
        Optional<ServiceCategory> catByName=servCategoryRepo.findByName(cat);
        Optional<ServiceCategory> servCat=servCategoryRepo.findById(id);
        if(servCat.isPresent() || catByName.isPresent()){
            return servCat.orElseGet(catByName::get);
        }else{
            ServiceCategory newServCat=new ServiceCategory();
            newServCat.setName("Service-category");
            return servCategoryRepo.save(newServCat);
        }
    }
};




























