package com.eCommerce.dtos.AdDto;

import com.eCommerce.enums.ItemCondition;
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
public class AdDto {

    private String productName;
    @Enumerated(EnumType.STRING)
    private ItemCondition itemCondition;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;
    private String productID;
    private String ort;
    private String sku;
    private double price;
    private String currency;
    private String description;
    private int quantity;
    private String imageURL;
    private String brand;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishDate;
    private String subcategoryName;

}
