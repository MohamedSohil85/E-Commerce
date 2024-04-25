package com.eCommerce.resources;

import com.eCommerce.dtos.accountdto.CustomerDto;
import com.eCommerce.entities.User;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.services.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)

public class CustomerResource {

    @Inject
    CustomerService customerService;

    @POST
    @Path("/signup")
    public Response signup(CustomerDto customerDto){
     Optional<User> customer_=customerService.findUserByEmail(customerDto.getEmail());
     if (customer_.isPresent()){
         return Response.status(Response.Status.FOUND).build();
     }
     customerService.saveNewCustomer(customerDto);
     return Response.status(Response.Status.CREATED).build();
    }
    @GET
    @Path("/customers")
    public Response loadAllCustomers() throws ResourceNotFoundException {
        List<CustomerDto>customers=customerService.loadAllCustomers();
        return Response.ok(customers).build();
    }
    @PUT
    @Path("/change/{id}/customer")
    public Response updateCustomerData(@PathParam("id")Long id,CustomerDto customerDto) throws ResourceNotFoundException {
        Optional<User> customerDtoOptional=customerService.findUserByEmail(customerDto.getEmail());
        if (customerDtoOptional.isPresent()){
            return Response.status(Response.Status.FOUND).build();
        }
        return Response.ok(customerService.changeCustomerDetailsById(id, customerDto)).build();
    }
    @PUT
    @Path("/forgetPassword")
    public Response sendNewPasswordByEmail(@QueryParam("email") String email) throws ResourceNotFoundException {
       return Response.ok(customerService.sendPasswordByEmail(email)).build();
    }
    @PUT
    @Path("/changePassword")
    public Response changePasswordByToken(@QueryParam("token")String token,User user) throws ResourceNotFoundException {
        return Response.ok(customerService.changeMyPassword(token, user)).build();
    }
    @GET
    @Path("/customers/{keyword}")
    public Response loadAllCustomersStartsWith(@PathParam("keyword")String keyword) throws ResourceNotFoundException {
        return Response.ok(customerService.getListOfCustomersStartWith(keyword)).build();
    }
    @DELETE
    @Path("/customer/{id}")
    public Response deleteById(@PathParam("id")Long id) throws ResourceNotFoundException {
        customerService.deleteUserById(id);
        return Response.noContent().build();
    }
}
