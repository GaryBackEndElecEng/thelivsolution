package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.ServiceMod;

import java.util.List;

public interface IServService {
    List<ServiceMod> findByCat(String cat);
    ServiceMod findService(String name);
    ServiceMod getService(Long servId);
    List<ServiceMod> getAllServices();
    ServiceMod saveService(ServiceMod service);

    ServiceMod updateService(ServiceMod service);
}
