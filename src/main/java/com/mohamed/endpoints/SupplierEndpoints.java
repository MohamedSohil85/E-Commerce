package com.mohamed.endpoints;

import com.mohamed.entities.Ad;
import com.mohamed.entities.Supplier;
import com.mohamed.repositories.AdRepository;
import com.mohamed.repositories.SupplierRepository;
import org.springframework.http.MediaType;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public class SupplierEndpoints {

    @Inject
    SupplierRepository supplierRepository;
    @Inject
    AdRepository adRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/Supplier")
    @Transactional
    public void createNewSupplier(@Valid Supplier supplier){
       String contactname=supplier.getContactName();
       String supplierAddress=supplier.getAddress();
       List<Supplier> supplierList=supplierRepository.listAll();
       supplierList.forEach(supplier1 -> {
           if (supplier1.getContactName().equalsIgnoreCase(contactname))
               if (supplier1.getAddress().equalsIgnoreCase(supplierAddress))
               {
                    Response.status(302).build();
               }

       });
       supplierRepository.persist(supplier);

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/SupplierWithProduct")
    @Transactional
    @RolesAllowed("Supplier")
    public Response saveProductToSupplier(@QueryParam("id")Long id,@QueryParam("name")String name){
        return supplierRepository.findByIdOptional(id).map(supplier -> {
            Optional<Ad>adOptional=adRepository.findAdByName(name).stream().findFirst();
            Ad ad=adOptional.get();
            ad.setSupplier(supplier);
            supplier.getAdList().add(ad);
            adRepository.persist(ad);
           return Response.status(Response.Status.CREATED).build();

        }).orElse(Response.noContent().build());
    }
}
