package com.eCommerce.entities;

import com.eCommerce.enums.ItemCondition;
import com.eCommerce.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Ad extends PanacheEntity {

    private String productName;
    private String sku;
    private String productID;
    @NotNull
    private double price;
    private String currency;
    private String description;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @NotNull
    private int quantity;
    private String imageURL;
    @Enumerated(EnumType.STRING)
    private ItemCondition itemCondition;
    private String ort;
    private String brand;
    @ManyToOne
    @JsonIgnore
    private SubCategory subCategory;
    @ManyToOne
    private Vendor vendor;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;
    private float productTotalReviews;
    private int noOfRating;
    @OneToOne
    private AverageRating averageRating;
    @OneToMany(mappedBy = "ad",cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Reviews> reviewsList;


}
