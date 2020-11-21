package com.mohamed.entities;



import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Customer extends PanacheEntity {

    private String lastName;
    private String firstName;
    private String address;
    private String phoneNumber;
    private String email;
    private String token;
}
