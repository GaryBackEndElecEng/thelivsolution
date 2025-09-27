package com.navinSecurity.thirdSecJWT.dto;

import com.navinSecurity.thirdSecJWT.model.Cart;
import lombok.*;

import java.util.Currency;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Charge {
    public static Charge create(Map<String, Object> chargeParams) {
        Charge newCharge=new Charge();
        newCharge.setAmount((Double) chargeParams.get("amount"));
        Currency curr=Charge.convertStringToCurrency((String) chargeParams.get("currency"));
        newCharge.setCurrency(curr);
        newCharge.setStripeToken((String) chargeParams.get("stripeToken"));
        newCharge.setDescription((String) chargeParams.get("description"));
        return newCharge;
    }

    public enum Currency {
        EUR, USD,CAD;
    }
    private String description;
    private Double amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;

    public static Currency convertStringToCurrency(String currency){
        Optional<Currency> curr=Optional.of( Currency.valueOf(currency));
        if(curr.isPresent()){
         return Currency.valueOf(currency);
        }else{
            return Currency.valueOf("USD");
        }
    }




}
