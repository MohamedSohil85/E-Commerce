package com.mohamed.endpoints;

import com.mohamed.entities.Customer;
import com.mohamed.entities.Role;
import com.mohamed.entities.ShoppingCart;
import com.mohamed.exceptions.ResourceNotFoundException;
import com.mohamed.repositories.CustomerRepository;
import com.mohamed.repositories.RoleRepository;
import com.mohamed.repositories.ShoppingCartRepository;
import io.quarkus.elytron.security.common.BcryptUtil;

import org.springframework.http.MediaType;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;



@Path("/api")
public class CustomerEndpoint {
    @Inject
    CustomerRepository customerRepository;
    @Inject
    RoleRepository roleRepository;
    @Inject
    ShoppingCartRepository shoppingCartRepository;

    @Path("/customers")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
    @RolesAllowed("Admin")
    public Response loadCustomers(){
   List<Customer>customers=customerRepository.getCusttomers();
   return Response.ok(customers).build();


    }

@Path("/Customer")
@POST
@Produces(MediaType.APPLICATION_JSON_VALUE)
@Transactional
public Response saveNewCustomer(@Valid Customer customer){
        String lastName= customer.getLastName();
        String address= customer.getAddress();
        String token= UUID.randomUUID().toString();
    Optional<Customer>optionalCustomer=customerRepository.findCustomer(lastName,address);
    if(optionalCustomer.isPresent()){
        return Response.status(302,"Object has been found").build();
    }
    customer.setToken(token);
    String pw= customer.getPassword();
    String encode= BcryptUtil.bcryptHash(pw);
    customer.setPassword(encode);
    customerRepository.persist(customer);
    return Response.status(Response.Status.CREATED).entity(customer).build();
}
@Path("findCustomerByName/{lastName}")
@GET
@Produces(MediaType.APPLICATION_JSON_VALUE)
public Response findCustomerBylastName(@PathParam("lastName")String lastName){
     List<Customer>Customers=customerRepository.findCustomerByName(lastName);
     if (Customers.isEmpty()){
         return Response.noContent().build();
     }
     return Response.status(Response.Status.FOUND).entity(Customers).build();
}
    @Path("/RoleByCustomerId/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Response saveRole(@PathParam("id")Long id,@Valid Role role){
        return customerRepository.findByIdOptional(id).map(customer -> {
            customer.getRoles().add(role);
            role.setCustomer(customer);
            roleRepository.persist(role);
            return Response.status(Response.Status.CREATED).build();

        }).orElse(Response.noContent().build());
    }
    @Path("/ShoppingCartByCustomerName/{customerName}/customer")
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Response createShoppingCartByCustomername(ShoppingCart shoppingCart, @PathParam("customerName")String customerName) throws ResourceNotFoundException {
        Optional<Customer>customerOptional=customerRepository.findBylastName(customerName);
        if(customerOptional.isEmpty()){
         throw new ResourceNotFoundException("Object not found");
        }
        shoppingCart=new ShoppingCart();
        Customer customer=customerOptional.get();
        String token=UUID.randomUUID().toString();
        shoppingCart.setCartCode(token);
        shoppingCart.setCustomer(customer);
        customer.setShoppingCart(shoppingCart);
        shoppingCartRepository.persist(shoppingCart);
        return Response.ok("Cart-Code :"+token).build();
    }

}
