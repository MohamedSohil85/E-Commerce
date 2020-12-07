package com.mohamed.endpoints;

import com.mohamed.entities.Customer;
import com.mohamed.entities.ShoppingCart;
import com.mohamed.exceptions.ResourceNotFoundException;
import com.mohamed.repositories.*;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Path("/api")
public class ShoppingCartEndpoints {

    @Inject
    ShoppingCartRepository shoppingCartRepository;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    AdRepository adRepository;


    @POST
    @Transactional
    @Path("/ShoppingCart/{customerName}/Customer/{productName}/Product")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response saveBidToShoppingCart(@PathParam("customerName")String customerName,@PathParam("productName")String productName,@Valid ShoppingCart shoppingCart){
        return adRepository.findAdByName(productName).map(ad -> {
            Optional<Customer>optionalCustomer=customerRepository.findBylastName(customerName);
            shoppingCart.setCustomer(optionalCustomer.get());
            shoppingCart.setOrderDate(new Date());
            shoppingCart.setTotal(ad.getPrice()*shoppingCart.getQuantity());
            ad.setShoppingCart(shoppingCart);
            shoppingCart.setAd(ad);
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
    // save products to shoppingcart


}
