package com.mohamed.endpoints;

import com.mohamed.entities.Payment;
import com.mohamed.exceptions.ResourceNotFoundException;
import com.mohamed.repositories.PaymentRepository;
import com.mohamed.repositories.ShoppingCartRepository;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.awt.*;

public class PaymentEndpoints {
    @Inject
    PaymentRepository paymentRepository;
    @Inject
    ShoppingCartRepository shoppingCartRepository;

    @Path("/Payment/{shoppingcartId}/ShoppingCart")
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response createPayment(@PathParam("shoppingcartId")Long shoppingcartId, @Valid Payment payment)throws ResourceNotFoundException {
        return shoppingCartRepository.findByIdOptional(shoppingcartId).map(shoppingCart -> {
        payment.setShoppingCart(shoppingCart);
        paymentRepository.persist(payment);
        return Response.status(Response.Status.CREATED).build();
        }).orElseThrow(()->new ResourceNotFoundException("Resource not found"));
    }
}
