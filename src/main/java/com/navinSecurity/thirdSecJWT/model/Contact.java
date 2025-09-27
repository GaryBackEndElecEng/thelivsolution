package com.navinSecurity.thirdSecJWT.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long contact_id;
    private String name;
    private String phone;
    private String email;
    private String department;
    @ManyToOne
    @JoinColumn(name="affiliate_id")
    @JsonBackReference("affiliate-contact")
    private Affiliate affiliate;

}
