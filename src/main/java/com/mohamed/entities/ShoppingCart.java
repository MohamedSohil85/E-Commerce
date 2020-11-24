package com.mohamed.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart extends PanacheEntity {
    @OneToMany
    private List<Request> RequestList;
    @OneToOne
    private Customer customer;
    @NotNull
    private int quantity;
    @NotNull
    private float total;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europa/Berlin")
    private Date OrderDate;


}
