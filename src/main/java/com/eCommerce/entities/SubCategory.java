package com.eCommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class SubCategory extends PanacheEntity {

    private String subcategoryName;
    @ManyToOne
    @JsonIgnore
    private Category category;
    @OneToMany(mappedBy = "subCategory",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Ad>adList;
}
