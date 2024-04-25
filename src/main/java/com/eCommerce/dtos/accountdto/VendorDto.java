package com.eCommerce.dtos.accountdto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VendorDto {

    @Size(min = 3,message = "min 3 character")
    private String lastName;
    @Size(min = 3 ,message = "min 3 character")
    private String firstName;
    @Size(min = 7,message = "min 3 character")
    private String phoneNumber;
    @Email
    private String email;
    private String token;
    private String cartCode;
    private String password;
    private String street;
    private String state;
    private String city;
    private int zip;
    private String country;
    private String companyName;
    private String fax;
    private String website;
}
