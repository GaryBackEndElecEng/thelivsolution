package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.ServiceMod;
import com.navinSecurity.thirdSecJWT.repo.ServiceRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServService implements IServService {

    @Autowired
    private ServiceRepo serviceRepo;


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
    public ServiceMod saveService(ServiceMod service) {
        return serviceRepo.save(service);
    }

    @Override
    public ServiceMod updateService(ServiceMod service) {
        Optional<ServiceMod> serv=serviceRepo.findById(service.getId());
        if(serv.isPresent()){
            return serviceRepo.save(service);
        }else{
            throw new RuntimeException("Service not found");
        }
    }
}
