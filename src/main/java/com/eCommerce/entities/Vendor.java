package com.eCommerce.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vendor extends User {


    private String companyName;
    private String fax;
    private String website;
    @JsonIgnore
    @OneToMany(mappedBy = "vendor")
    private List<Ad> adList;
}
