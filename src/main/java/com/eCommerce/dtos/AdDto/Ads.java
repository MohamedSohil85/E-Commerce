package com.eCommerce.dtos.AdDto;

import com.eCommerce.dtos.reviews.ReviewsDto;
import com.eCommerce.entities.Reviews;
import com.eCommerce.enums.ItemCondition;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ads {
    private String productName;
    private String productID;
    @Enumerated(EnumType.STRING)
    private ItemCondition itemCondition;
    private double price;
    private String currency;
    private String description;
    private String ort;
    private int quantity;
    private String imageURL;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;
    private String brand;
    private String firstName;
    private String lastName;
    private String companyName;
    private String fax;
    private String phoneNumber;
    private String email;
    private List<ReviewsDto> reviewsList;
}
