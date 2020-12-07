package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Supplier extends PanacheEntity {
    private String ContactName;
    private String address;
    private String phoneNumber;
    @OneToMany
    private List<Ad> adList;
}
