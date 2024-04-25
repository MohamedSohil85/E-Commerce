package com.eCommerce.dtos.AdDto;

import com.eCommerce.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WishDto {

    private String productName;
    private double price;
    private String currency;
    private String description;
    private String brand;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date addToWishCart;
}
