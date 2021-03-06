package com.mohamed.endpoints;

import com.mohamed.entities.Ad;
import com.mohamed.exceptions.ResourceNotFoundException;
import com.mohamed.repositories.AdRepository;
import com.mohamed.repositories.CategoryRepository;
import io.quarkus.panache.common.Parameters;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api")
public class AdEndpoints {
    @Inject
     AdRepository adRepository;
    @Inject
   CategoryRepository categoryRepository;



    @Path("/Ad")
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Response createRequest(@Valid Ad request){
       adRepository.persist(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/Ads")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response getAds()throws ResourceNotFoundException {
        List<Ad>adList= adRepository.getlist();
        if (adList.isEmpty()){
            throw new ResourceNotFoundException("Resource not found");
        }
        return Response.status(Response.Status.FOUND).entity(adList).build();
    }
    
    @POST
    @Path("/BidByCategory/{categoryName}")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response saveAdtoCategory(@Valid Ad bid,@PathParam("categoryName")String categoryName)throws ResourceNotFoundException{
        return categoryRepository.findCategoryByName(categoryName).map(category -> {
            bid.setCategory(category);
            bid.setPublishDate(new Date());
            category.getAdList().add(bid);
            adRepository.persist(bid);
            return Response.status(Response.Status.CREATED).build();
        }).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
    }


    @GET
    @Path("/findAdByName/{name}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findProductByName(@PathParam("name")String name){
        Optional<Ad>optionalBid=adRepository.findAdByName(name);
        return Response.ok(optionalBid).build();
    }
    @GET
    @Path("/findAdByKeyword/{searchkey}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Ad>findProductBykeyword(@PathParam("searchkey")String keyword)throws ResourceNotFoundException{

        
        List<Ad>adList= adRepository
                .stream("from Ad where productName like CONCAT(:keyword,'%')", Parameters.with("keyword",keyword))
                .collect(Collectors.toList());
        if (adList.isEmpty()){
            throw new ResourceNotFoundException("Resource not found");
        }

        return adList;
    }

}
