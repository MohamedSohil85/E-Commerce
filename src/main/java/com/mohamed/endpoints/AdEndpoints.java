package com.mohamed.endpoints;

import com.mohamed.entities.Ad;
import com.mohamed.entities.Bid;
import com.mohamed.entities.Request;
import com.mohamed.exceptions.ResourceNotFoundException;
import com.mohamed.repositories.AdRepository;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

public class AdEndpoints {
    @Inject
    AdRepository adRepository;

    @Path("/Request")
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response createRequest(@Valid Request request){
       adRepository.persist(request);
        return Response.status(Response.Status.CREATED).build();
    }
    @POST
    @Path("/Bid")
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


}
