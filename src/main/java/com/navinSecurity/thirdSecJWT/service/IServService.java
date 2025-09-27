package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.dto.ServiceDtoCreate;
import com.navinSecurity.thirdSecJWT.model.ServiceMod;

import java.util.List;

public interface IServService {
    List<ServiceMod> findByCat(String cat);
    ServiceMod findService(String name);
    ServiceMod getService(Long servId);
    List<ServiceMod> getAllServices();
    ServiceMod saveService(ServiceDtoCreate service, Long user_id, Long id);

    ServiceMod updateService(ServiceMod service,Long user_id);

    String deleteService(Long userId,Long servId);
}
