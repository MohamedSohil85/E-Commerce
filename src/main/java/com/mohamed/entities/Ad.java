package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Ad extends PanacheEntity {
    @Size(min = 3,message = "3 character at Least")
    private String productName;
    @NotNull
    private float price;
    @Size(min = 3,message = "3 character at Least")
    private String description;
    @Size(min = 3,message = "3 character at Least")
    private String standort;
    @NotNull
    private int quantity;
    @ManyToOne
    private Category category;
    @ManyToOne
    private ShoppingCart shoppingCart;
    @ManyToOne
    private Supplier supplier;
}
