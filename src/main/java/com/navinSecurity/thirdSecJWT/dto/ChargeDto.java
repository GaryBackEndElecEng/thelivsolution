package com.navinSecurity.thirdSecJWT.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargeDto {
    private String description;
    private Double amount;
    private Charge.Currency currency;

    public ChargeDto convert(Charge charge){
        return ChargeDto.builder()
                .description(charge.getDescription())
                .amount(charge.getAmount())
                .currency(charge.getCurrency())
                .build();
    }
}
