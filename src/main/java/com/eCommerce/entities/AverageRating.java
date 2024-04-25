package com.eCommerce.entities;

import com.eCommerce.enums.Rating;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AverageRating extends PanacheEntity {
    @OneToOne
    private Ad ad;
    private Rating rating;
    private int noOfRating;
    private int totalNumberOfRating;


}
