package com.mohamed.entities;



import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Setter
@Getter
@Entity
public class Customer extends PanacheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;
    private String address;
    private String phoneNumber;
    private String email;
}
