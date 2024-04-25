package com.eCommerce.resources;

import com.eCommerce.entities.CartItem;
import com.eCommerce.exceptions.ResourceNotFoundException;
import com.eCommerce.services.ShoppingCartService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShoppingCartResource {
@Inject
ShoppingCartService shoppingCartService;



    @Path("/cart/{productId}/cartCode/{shoppingCartId}/quantity")
    @POST
    public Response addItemToCart(@PathParam("productId")Long id, @PathParam("shoppingCartId")Long shoppingCartId,@QueryParam("quantity")int qty){
        return Response.ok(shoppingCartService.addItemToCard(id, shoppingCartId,qty)).build();
    }
  @Path("shoppingCart/{code}")
  @GET
    public Response findByCartCode(@PathParam("code")String code) throws ResourceNotFoundException {
        return Response.ok(shoppingCartService.fetchShoppingCartByCartCode(code)).build();
  }
@Path("/total price/{productId}/cart/{cartId}/qty")
    @POST
    public Response getTotalprice(@PathParam("productId") Long productId,@PathParam("cartId")Long cartId,@QueryParam("quantity")int qty){
        return Response.ok(shoppingCartService.getTotalPriceByShoppingCartId(productId, cartId, qty)).build();
}
/*@Path("/addProduct/{adId}/shoppingCartId/{cartId}")
    @POST
    public Response addProductToCart(@PathParam("adId")Long adId,@PathParam("cartId")Long cartId,CartItem cartItem){
        return Response.ok(shoppingCartService.addProductToCart(adId,cartId,cartItem)).build();
}*/
@Path("getTotalPrice/{productId}/cart/{cartId}/quantity")
    @POST
       public Response addItemsToCart(@PathParam("productId") Long productId,@PathParam("cartId")Long cartId,@QueryParam("quantity")int qty){
        return Response.ok(shoppingCartService.getTotalPriceByCartId(productId, cartId, qty)).build();
}
    @Path("/{cartItemId}/cartId/{cartId}")
    @DELETE
    public Response deleteCartItemById(@PathParam("cartItemId")Long cartItemId,@PathParam("cartId")Long cartId){
        return Response.ok(shoppingCartService.removeCartItem(cartItemId,cartId)).build();
   }
   @Path("/changeItemQty/cartItemId/{cartItemId}/cartId/{cartId}/productId/{productId}/quantity")
    @PUT
    public Response changeItemQty(@PathParam("cartItemId")Long cartItemId,@PathParam("cartId")Long cartId,@PathParam("productId")Long productId,@QueryParam("qty")int qty) throws ResourceNotFoundException {
        return Response.ok(shoppingCartService.changeItemQuantity(cartItemId, cartId, productId,qty)).build();
   }
  @Path("/removeAllItems/{id}")
    @DELETE
    public Response removeALlItemsById(@PathParam("id")Long id){
        return Response.ok(shoppingCartService.removeAllItems(id)).build();
  }
}
