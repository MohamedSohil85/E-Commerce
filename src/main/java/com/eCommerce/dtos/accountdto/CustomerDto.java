package com.eCommerce.dtos.accountdto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerDto {

    @Size(min = 3,message = "min 3 character")
    private String lastName;
    @Size(min = 3 ,message = "min 3 character")
    private String firstName;
    @Size(min = 7,message = "min 3 character")
    private String phoneNumber;
    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$")
    private String email;
    private String cartCode;
    private String password;
    private String street;
    private String city;
    private int zip;
    private String state;
    private String country;

}
