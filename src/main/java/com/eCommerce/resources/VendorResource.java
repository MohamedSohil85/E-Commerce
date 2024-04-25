package com.eCommerce.resources;

import com.eCommerce.dtos.accountdto.VendorDto;
import com.eCommerce.entities.User;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.services.VendorService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VendorResource {
    @Inject
    VendorService vendorService;
    @POST
    @Path("/vendor")
    public Response saveNewVendor(VendorDto vendorDto){
        Optional<User> vendor_= vendorService.findUserByEmail(vendorDto.getEmail());
        if (vendor_.isPresent()){
            return Response.status(Response.Status.FOUND).build();
        }
     vendorService.saveNewVendor(vendorDto);
     return Response.status(Response.Status.CREATED).build();
    }
    @Path("/vendors/index/{index}/size/{size}")
    @GET
    public Response loadAllVendors(@PathParam("index")int index,@PathParam("size")int size) throws ResourceNotFoundException {
        return Response.ok(vendorService.loadAllVendors(index, size)).build();
    }
    @Path("/update/{id}/vendor")
    @PUT
    public Response editVendorById(@PathParam("id")Long id,VendorDto vendorDto) throws ResourceNotFoundException {
        return Response.ok(vendorService.changeVendorDataById(id, vendorDto)).build();
    }
    }
