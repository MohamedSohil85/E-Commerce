package com.eCommerce.resources;

import com.eCommerce.dtos.AdDto.CategoryDto;
import com.eCommerce.dtos.AdDto.SubCategoryDto;
import com.eCommerce.entities.SubCategory;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.services.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(value = MediaType.APPLICATION_JSON)
@Consumes(value = MediaType.APPLICATION_JSON)
public class CategoryResource {
  @Inject
    CategoryService categoryService;
    @POST
    @Path("/category")
    public Response addNewCategory(CategoryDto categoryDto){
        return Response.ok(categoryService.createNewCategory(categoryDto)).build();
    }
    @POST
    @Path("/subcategory/{categoryName}")
    public Response addSubcategory(@PathParam("categoryName")String name, SubCategory subCategory) throws ResourceNotFoundException {
        return Response.ok(categoryService.createSubCategory(subCategory,name)).build();
    }
    @GET
    @Path("/categories")
  public Response loadAllCategories() throws ResourceNotFoundException {
      return Response.ok(categoryService.loadAllCategories()).build();
    }
  @GET
  @Path("/subcategories")
  public Response loadAllSubcategories() throws ResourceNotFoundException {
      return Response.ok(categoryService.loadAllSubcategory()).build();
  }
}
