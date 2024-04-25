package com.eCommerce.dtos.OrderDto;

import com.eCommerce.entities.CartItem;
import com.eCommerce.entities.ShoppingCart;
import com.eCommerce.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    private String accountNumber;
    @Size(min = 3,message = "3 character at Least")
    private String cardType;
    @Size(min = 3,message = "3 character at Least")
    private String cardholderName;
    private String expirationDate;
    private String cvv;
    private String street;
    private String city;
    private int zip;
    private String state;
    private String country;
    private double totalPrice;
    private List<CartItem> cartItems;
    @Temporal(value = TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderDate;
    private String lastName;
    @Size(min = 3 ,message = "min 3 character")
    private String firstName;
}
