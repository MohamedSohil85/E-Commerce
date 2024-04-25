package com.eCommerce.resources;

import com.eCommerce.dtos.OrderDto.OrderDto;
import com.eCommerce.services.OrderService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Consumes(value = MediaType.APPLICATION_JSON)
@Produces(value = MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @Path("/order/{cartId}")
    @POST
    public Response makeNewOrder(@PathParam("cartId")Long id, OrderDto orderDto){
        return Response.ok(orderService.makeOrder(id, orderDto)).build();
    }
}
