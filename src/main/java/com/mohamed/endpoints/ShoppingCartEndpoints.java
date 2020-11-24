package com.mohamed.endpoints;

import com.mohamed.entities.Customer;
import com.mohamed.entities.Request;
import com.mohamed.entities.ShoppingCart;
import com.mohamed.exceptions.ResourceNotFoundException;
import com.mohamed.repositories.AdRepository;
import com.mohamed.repositories.CustomerRepository;
import com.mohamed.repositories.ShoppingCartRepository;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ShoppingCartEndpoints {

    @Inject
    ShoppingCartRepository shoppingCartRepository;
    @Inject
    AdRepository adRepository;
    @Inject
    CustomerRepository customerRepository;

    @POST
    @Path("/ShoppingCart/{customerName}/Customer/{productName}/Product")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response saveToShoppingCart(@PathParam("customerName")String customerName,@PathParam("productName")String productName, @Valid ShoppingCart shoppingCart){
        return adRepository.findAdByName(productName).map(request -> {
            Optional<Customer>optionalCustomer=customerRepository.findBylastName(customerName);
            shoppingCart.setCustomer(optionalCustomer.get());
            shoppingCart.setOrderDate(new Date());
            shoppingCart.setTotal(request.getPrice()*shoppingCart.getQuantity());
            request.setShoppingCart(shoppingCart);
            shoppingCart.getRequestList().add(request);
            shoppingCartRepository.persist(shoppingCart);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }
    @GET
    @Path("/shoppingcarts")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<ShoppingCart>loadAllShoppingCarts()throws ResourceNotFoundException{
        List<ShoppingCart>shoppingCarts=shoppingCartRepository.listAll();
        if (shoppingCarts.isEmpty()){
         throw new ResourceNotFoundException("Rescource not found");
        }
        return shoppingCarts;
    }

}
