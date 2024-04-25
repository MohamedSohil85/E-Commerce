package com.eCommerce.resources;

import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.services.WishItemCartService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class WishItemResource {

    @Inject
    WishItemCartService wishItemCartService;

    @Path("/wishItem/{productName}/user/{userId}")
    @POST
    public Response addItemToWishList(@PathParam("productName")String name,@PathParam("userId")Long userId) throws ResourceNotFoundException {
        wishItemCartService.addItemToWishList(name, userId);
        return Response.status(Response.Status.CREATED).build();
    }
    @Path("/deleteById/{id}")
    @DELETE
    public Response deleteWishItemById(@PathParam("id")Long id){
        wishItemCartService.removeItemFromListById(id);
        return Response.ok("Item deleted from WishItem List").build();
    }
    @Path("/wishItems/{id}")
    @GET
    public Response loadWishItemsById(@PathParam("id")Long id) throws ResourceNotFoundException {
        return Response.ok(wishItemCartService.loadItemsFromListByUserId(id)).build();
    }
}
