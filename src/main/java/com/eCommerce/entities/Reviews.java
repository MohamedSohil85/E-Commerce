package com.eCommerce.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reviews extends PanacheEntity {

    private int rating;
    private String comment;
    @JsonFormat(pattern = "dd-MM-YYYY HH:mm:ss")
    private Date dateOfReview;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Ad ad;
    @OneToOne
    private User user;
}
