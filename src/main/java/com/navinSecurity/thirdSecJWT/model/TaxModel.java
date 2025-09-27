package com.navinSecurity.thirdSecJWT.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Percentage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String country;
    private String prov_state;
    private Double stateProvPerc;
    private Double fedPerc;

    public TaxModel(String country,String prov_state,Double stateProvPerc,Double fedPerc){
        this.country=country;
        this.prov_state=prov_state;
        this.stateProvPerc=stateProvPerc;
        this.fedPerc=fedPerc;
    }


    public Double calcTotalAmount(Double principal){
        Double calcTax=this.calcProv_state(principal) + this.calcFed(principal);
        return principal + calcTax;
    }
    public Double calcProv_state(Double principal){
        return principal * this.stateProvPerc;
    }
    public Double calcFed(Double principal){
        return principal * this.fedPerc;
    }




}
