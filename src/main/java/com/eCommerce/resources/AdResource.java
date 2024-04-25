package com.eCommerce.resources;


import com.eCommerce.dtos.AdDto.AdDto;

import com.eCommerce.enums.ItemCondition;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.services.AdService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class AdResource {

    @Inject
    AdService adService;

    // save new Ad
   @POST
    @Path("/ad/{vendorId}/subcategory/{subcategory}")
    public Response addNewProduct(@PathParam("vendorId")Long id, @PathParam("subcategory")String subcategory, AdDto adDto) throws ResourceNotFoundException {
       adService.addNewProduct(id, subcategory, adDto);
       return Response.status(Response.Status.CREATED).build();
   }
    @GET
    @Path("/ads")
    public Response findAdsBySubcategory(@QueryParam("subCategory") String subCategory )throws ResourceNotFoundException {
       return Response.ok(adService.findAdsBySubCategory(subCategory)).build();
    }

    @GET
    @Path("/ads/{brand}")
    public Response findAdsByBrand(@PathParam("brand")String brand) throws ResourceNotFoundException {
       return Response.ok(adService.loadAdsByBrandName(brand)).build();
    }


    @GET
    @Path("/ads/index/{index}/size/{size}")
    public Response findAdsBy(@QueryParam("name")String productName, @QueryParam("ItemCondition")ItemCondition itemCondition,@PathParam("index")int index,@PathParam("size")int size) throws ResourceNotFoundException {
       return Response.ok(adService.fetchAdsByNameAndItemCondition(productName,itemCondition,index,size)).build();
    }
   @GET
    @Path("/find-ads/index/{index}/size/{size}")
    public Response filterAdsBy(@QueryParam("name") String name,
                                @QueryParam("itemCondition") ItemCondition itemCondition,
                                @QueryParam("ort") String ort,
                                @QueryParam("min") double min,
                                @QueryParam("max") double max,
                                @PathParam("index") int index,
                                @PathParam("size") int size) throws ResourceNotFoundException {
    return Response.ok(adService.fetchAdsBy(name,itemCondition,ort,min,max,index,size)).build();
    }
    @DELETE
    @Path("/delete/{id}")
    public Response deleteAdById(@PathParam("id")Long id) throws ResourceNotFoundException {
        adService.deleteAdById(id);
       return Response.ok("Ad has been deleted !").build();
    }
    @PUT
    @Path("/edit/{productId}/product")
    public Response updateAd(@PathParam("productId")Long id,AdDto newAd) throws ResourceNotFoundException {
       return Response.ok(adService.updateProductInfo(id, newAd)).build();
    }
}
