package com.mohamed.endpoints;

import com.mohamed.entities.Ad;
import com.mohamed.entities.Bid;
import com.mohamed.entities.Category;
import com.mohamed.entities.Request;
import com.mohamed.exceptions.ResourceNotFoundException;
import com.mohamed.repositories.AdRepository;
import com.mohamed.repositories.CategoryRepository;
import org.hibernate.ResourceClosedException;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
@Path("/api")
public class AdEndpoints {
    @Inject
    private AdRepository adRepository;
    @Inject
    private CategoryRepository categoryRepository;


    @Path("/Request")
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Response createRequest(@Valid Request request){
       adRepository.persist(request);
        return Response.status(Response.Status.CREATED).build();
    }
    @POST
    @Path("/Bid")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response createBid(@Valid Bid bid){
        adRepository.persist(bid);
        return Response.status(Response.Status.CREATED).build();
    }
    @GET
    @Path("/Ads")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response getAds()throws ResourceNotFoundException {
        List<Ad>adList=adRepository.getlist();
        if (adList.isEmpty()){
            throw new ResourceNotFoundException("Resource not found");
        }
        return Response.status(Response.Status.FOUND).entity(adList).build();
    }
    
    @POST
    @Path("/BidByCategory/{categoryName}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response saveAdtoCategory(@Valid Bid bid,@PathParam("categoryName")String categoryName)throws ResourceNotFoundException{
        return categoryRepository.findCategoryByName(categoryName).map(category -> {
            bid.setCategory(category);
            category.getAdList().add(bid);
            adRepository.persist(bid);
            return Response.status(Response.Status.CREATED).build();
        }).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
    }

    @POST
    @Path("/RequestByCategory/{categoryName}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response saveRequesttoCategory(@Valid Request request,@PathParam("categoryName")String categoryName)throws ResourceNotFoundException{
        return categoryRepository.findCategoryByName(categoryName).map(category -> {
            request.setCategory(category);
            category.getAdList().add(request);
            adRepository.persist(request);
            return Response.status(Response.Status.CREATED).build();
        }).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
    }
    

}
