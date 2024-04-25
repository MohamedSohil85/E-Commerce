package com.eCommerce.dtos.reviews;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewsDto {

    private String username;
    private int rating;
    private String comment;
    @JsonFormat(pattern = "dd-MM-YYYY HH:mm:ss")
    private Date dateOfReview;

}
