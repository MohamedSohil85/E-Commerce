package com.mohamed.endpoints;

import com.mohamed.entities.Category;
import com.mohamed.exceptions.ResourceNotFoundException;
import com.mohamed.repositories.CategoryRepository;
import io.quarkus.panache.common.Sort;
import org.springframework.http.MediaType;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
@Path("/api")
public class CategoryEndpoints {
    @Inject
    CategoryRepository categoryRepository;

    @Path("/Category")
    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Response createCategory(@Valid Category category){
        String name=category.getCategoryName();
        Optional<Category>optionalCategory=categoryRepository.findCategoryByName(name);
       if(optionalCategory.isPresent()){
           return Response.status(Response.Status.FOUND).build();
       }
       categoryRepository.persist(category);
       return Response.status(Response.Status.CREATED).entity(category).build();
    }
    @GET
    @Path("/Categories")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response loadCategories() throws ResourceNotFoundException {
        List<Category>categories=categoryRepository.listAll(Sort.descending("categoryName"));
        if(categories.isEmpty()){
            throw new ResourceNotFoundException("not Found !");
        }
        return Response.ok(categories).build();
    }

}
