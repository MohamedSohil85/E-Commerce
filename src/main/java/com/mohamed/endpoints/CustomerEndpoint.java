package com.mohamed.endpoints;

import com.mohamed.entities.Customer;
import com.mohamed.repositories.CustomerRepository;
import io.quarkus.panache.common.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;


@RestController
public class CustomerEndpoint {
    @Inject
    CustomerRepository customerRepository;

    @Path("/customers")
    @GET
    @Produces(value = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> loadCustomers(){
      List<Customer> customerList= (List<Customer>) customerRepository.findAll(Sort.ascending("lastName"));
      return new ResponseEntity(customerList, HttpStatus.FOUND);

    }

@Path("/Customer")
@POST
@Produces(MediaType.APPLICATION_JSON_VALUE)
public Response saveNewCustomer(@RequestBody Customer customer){
        String lastName= customer.getLastName();
        String address= customer.getAddress();
    Optional<Customer>optionalCustomer=customerRepository.findCustomer(lastName,address);
    if(optionalCustomer.isPresent()){
        return Response.status(302,"Object has been found").build();
    }
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

}
