package com.eCommerce.entities;

import com.eCommerce.enums.PaymentMethod;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Payment extends PanacheEntity {
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String accountNumber;
    @Size(min = 3,message = "3 character at Least")
    private String cardType;
    @Size(min = 3,message = "3 character at Least")
    private String cardholderName;
    private String expirationDate;
    private String cvv;


}
