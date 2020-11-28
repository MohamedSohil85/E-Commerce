package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Payment extends PanacheEntity {

    private String accountNumber;
    @Size(min = 3,message = "3 character at Least")
    private String passWord;
    @Size(min = 3,message = "3 character at Least")
    private String cardType;
    @Size(min = 3,message = "3 character at Least")
    private String cardholderName;
    @OneToOne
    ShoppingCart shoppingCart;
}
