package com.mohamed.endpoints;

import com.mohamed.entities.Orders;
import com.mohamed.entities.ShoppingCart;
import com.mohamed.repositories.CustomerRepository;
import com.mohamed.repositories.OrderRepository;
import com.mohamed.repositories.ShoppingCartRepository;
import lombok.ToString;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.Optional;

@Path("/api")
public class OrderEndpoints {
    @Inject
    OrderRepository orderRepository;
    @Inject
    CustomerRepository customerRepository;
    @Inject
    ShoppingCartRepository shoppingCartRepository;

    @Path("/OrderByCustomerName/{name}/Customer/CartId/{id}")
    @Transactional
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response makeOrder(@PathParam("name")String name,@PathParam("id")Long id, @Valid Orders orders){
        return customerRepository.findBylastName(name).map(customer -> {
            Optional<ShoppingCart>shoppingCartOptional=shoppingCartRepository.findByIdOptional(id);
            orders.setOrderDate(new Date());
            orders.setCustomer(customer);
            orders.setShoppingCart(shoppingCartOptional.get());
            orderRepository.persist(orders);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }


}
