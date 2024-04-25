package com.eCommerce.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart extends PanacheEntity {

    private String cartCode;
    private double totalPrice;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date OrderDate;
    @OneToMany(mappedBy = "shoppingCart",fetch = FetchType.EAGER)
    private List<CartItem>cartItems;
    @OneToOne
    private User user;




}
