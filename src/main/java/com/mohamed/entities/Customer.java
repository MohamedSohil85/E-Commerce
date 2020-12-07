package com.mohamed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@UserDefinition
public class Customer extends PanacheEntity {
    @Size(min = 3,message = "min 3 character")
    private String lastName;
    @Size(min = 3 ,message = "min 3 character")
    private String firstName;
    @Size(min = 3,message = "min 3 character")
    private String address;
    @Size(min = 7,message = "min 3 character")
    private String phoneNumber;
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$")
    private String email;
    private String token;
    @Username
    private String username;
    @Password
    private String password;
    @OneToOne
    private Payment payment;
    @OneToOne

    private ShoppingCart shoppingCart;
    @Roles
    @OneToMany
    @JsonIgnore
    private List<Role> roles=new ArrayList<>();
}
