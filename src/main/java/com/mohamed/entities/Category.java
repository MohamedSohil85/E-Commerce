package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Category extends PanacheEntity {
    @Size(min = 3,message = "input Error !")
    private String categoryName;
    @OneToMany
    private List<Ad>adList;
}
