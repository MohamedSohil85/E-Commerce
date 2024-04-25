package com.eCommerce.resources;

import com.eCommerce.dtos.reviews.ReviewsDto;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.services.ReviewsService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class ReviewsResource {

    @Inject
    ReviewsService reviewsService;

    @Path("/review/{userToken}/name/{productName}")
    public Response addReview(@PathParam("userToken")String token, @PathParam("productName")String name, ReviewsDto reviewsDto) throws ResourceNotFoundException {
        return Response.ok(reviewsService.addComment(token, name, reviewsDto)).build();
    }
}
