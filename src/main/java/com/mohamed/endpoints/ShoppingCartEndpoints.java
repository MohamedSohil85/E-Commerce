package com.mohamed.endpoints;

import com.mohamed.entities.Ad;
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
    @Path("/ShoppingCart/{id}/Customer/{productName}/Product")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response saveBidToShoppingCart(@PathParam("id")Long customerName,@PathParam("productName")String productName){
       return customerRepository.findByIdOptional(customerName).map(customer -> {
       Optional<Ad>optionalAd=adRepository.findAdByName(productName);
       Optional<ShoppingCart>optionalShoppingCart=shoppingCartRepository.findShoppingCartByCustomerName(customerName);
       Ad ad=optionalAd.get();
       ShoppingCart cart=optionalShoppingCart.get();

       cart.getAdList().add(ad);
       ad.setShoppingCart(cart);
       customer.setShoppingCart(cart);
       shoppingCartRepository.persist(cart);
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

    @POST
    @Transactional
    @Path("/ShoppingCart/{cartToken}/Ad/{productName}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response addProductToCart(@PathParam("cartToken")String token,@PathParam("productName")String name){
        return shoppingCartRepository.findCartByToken(token).map(shoppingCart -> {
            Optional<Ad>optionalAd=adRepository.findAdByName(name);
            Ad ad=optionalAd.get();
            shoppingCart.setTotal(shoppingCart.getQuantity()*ad.getPrice());
            shoppingCart.getAdList().add(ad);
            ad.setShoppingCart(shoppingCart);
            adRepository.persist(ad);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }
}
