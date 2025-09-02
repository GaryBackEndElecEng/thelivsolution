package com.navinSecurity.thirdSecJWT.service;

import com.navinSecurity.thirdSecJWT.model.Product;
import com.navinSecurity.thirdSecJWT.model.ProductCategory;
import com.navinSecurity.thirdSecJWT.model.ServiceCategory;
import com.navinSecurity.thirdSecJWT.model.ServiceMod;
import com.navinSecurity.thirdSecJWT.repo.ServCategoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServCatService  implements IServCatService{

    @Autowired
    private final ServCategoryRepo servCategoryRepo;

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
    public String deleteCategory(Long categoryId) {
        Optional<ServiceCategory> option=servCategoryRepo.findById(categoryId);
        option.ifPresent(servCategoryRepo::delete);
        return "deleted: " + categoryId;
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
    public ServiceCategory post(ServiceCategory servCat) {
        List<ServiceMod>serviceMods=new ArrayList<>();
        final boolean isPost_added = serviceMods.addAll(servCat.getServices());
        if (!isPost_added) {
            servCat.setServices(serviceMods);
        }
        return servCategoryRepo.save(servCat);

    };
};

































