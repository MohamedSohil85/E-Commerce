package com.mohamed.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
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
    @JsonIgnore
    private Category category;
    @ManyToOne
    private Supplier supplier;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date publishDate;
    @OneToOne
    private ShoppingCart shoppingCart;

}
