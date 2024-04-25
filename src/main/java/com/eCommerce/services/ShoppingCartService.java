package com.eCommerce.services;


import com.eCommerce.entities.Ad;
import com.eCommerce.entities.CartItem;
import com.eCommerce.entities.ShoppingCart;
import com.eCommerce.enums.ProductStatus;
import com.eCommerce.exceptions.ResourceNotFoundException;

import com.eCommerce.persistence.CartItemRepository;
import com.eCommerce.persistence.ShoppingCartRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ShoppingCartService {
    @Inject
    ShoppingCartRepository shoppingCartRepository;
    @Inject
    CartItemRepository cartItemRepository;

    @Transactional
    public ShoppingCart addItemToCard(Long productId, Long shoppingCartId, int cartItemQuantity){
        CartItem cartItem=new CartItem();
        Ad ad=Ad.findById(productId);
        ShoppingCart shoppingCart=shoppingCartRepository.findById(shoppingCartId);

        if (ad.getProductStatus().equals(ProductStatus.AVAILABLE)
                || cartItemQuantity < ad.getQuantity()) {
            cartItem.setPrice(ad.getPrice() * cartItemQuantity);
            cartItem.setQuantity(cartItemQuantity);
            cartItem.setAd(ad);
            shoppingCart.setOrderDate(new Date());
            shoppingCart.getCartItems().add(cartItem);
            List<CartItem> cartItems = cartItemRepository.stream("ShoppingCart_id", shoppingCartId).toList();
            double sum = 0.0d;
            double single_price = cartItem.getPrice();
            for (CartItem item : cartItems) {

                sum += item.getPrice();

            }
            int qnt = ad.getQuantity();
            qnt=qnt-cartItemQuantity;

            if (qnt == 0) {
                ad.setProductStatus(ProductStatus.NOT_AVAILABLE);

            }
            ad.setQuantity(qnt);
            shoppingCart.setTotalPrice(sum + single_price);

            cartItem.setShoppingCart(shoppingCart);
            shoppingCartRepository.persist(shoppingCart);



            ad.persist();
            cartItem.persist();
        }
        return shoppingCart;
    }
    @Transactional
    public double getTotalPriceByShoppingCartId(Long productId,Long shoppingCartId, int cartItemQuantity){
        CartItem cartItem=new CartItem();
        ShoppingCart shoppingCart=shoppingCartRepository.findById(shoppingCartId);
        Ad ad=Ad.findById(productId);
        cartItem.setAd(ad);
        cartItem.setQuantity(cartItemQuantity);
        cartItem.setPrice(ad.getPrice()*cartItem.getQuantity());
        double singleItem=cartItem.getPrice();
        int qnt=ad.getQuantity();
        qnt--;
        ad.setQuantity(qnt);
        ad.persist();
        double sum=+cartItemRepository.stream("ShoppingCart_id",shoppingCartId).mapToDouble(CartItem::getPrice).sum();
        shoppingCart.setOrderDate(new Date());
        shoppingCart.getCartItems().add(cartItem);
        shoppingCart.setTotalPrice(sum+singleItem);
        shoppingCartRepository.persist(shoppingCart);
        cartItem.setShoppingCart(shoppingCart);
        cartItem.persist();

        return sum;
    }
    public ShoppingCart fetchShoppingCartByCartCode(String cartCode)throws ResourceNotFoundException{
        return shoppingCartRepository.find("cartCode",cartCode).singleResultOptional().orElseThrow(()->new ResourceNotFoundException("Resource with code :"+cartCode+" not found"));
    }


   public ShoppingCart addProductToCart(Long productId,Long shoppingId,CartItem cartItem){
        Ad ad=Ad.findById(productId);
        ShoppingCart shoppingCart=ShoppingCart.findById(shoppingId);
        List<CartItem>cartItems=cartItemRepository.listAll();
        for (CartItem cartItem_:cartItems){
            if (ad.getQuantity()<cartItem.getQuantity())
                if((cartItem_.getAd().equals(ad)&&cartItem_.getShoppingCart().equals(shoppingCart))){
                    double sum;
                    sum=+cartItem.getPrice();

                    int qnt=ad.getQuantity();
                    qnt--;
                    ad.setQuantity(qnt);
                    ad.persist();
                    cartItem.setShoppingCart(shoppingCart);
                    cartItem.persist();
                    shoppingCart.setTotalPrice(sum);
                    shoppingCartRepository.persistAndFlush(shoppingCart);
                }
        }
        return shoppingCart;
   }
   @Transactional
   public double getTotalPriceByCartId(Long productId,Long cartId,int qty){
       Ad ad=Ad.findById(productId);
        ShoppingCart shoppingCart=shoppingCartRepository.findById(cartId);
        List<CartItem>cartItems=shoppingCart.getCartItems();
        double sum=0.0d;
        for (CartItem cartItem:cartItems){
            sum = cartItemRepository.stream("ShoppingCart_id", cartId).mapToDouble(CartItem::getPrice).sum();



        }

       return sum;
   }
    @Transactional
   public ShoppingCart removeCartItem(Long cartItemId,Long shoppingCartId){
        CartItem cartItem=CartItem.findById(cartItemId);


        int qnt=cartItem.getQuantity();
        Ad ad=cartItem.getAd();
        int totalQty=ad.getQuantity()+qnt;
        ad.setQuantity(totalQty);
        CartItem.deleteById(cartItemId);
    ShoppingCart shoppingCart=shoppingCartRepository.findById(shoppingCartId);
       double sum = cartItemRepository.stream("ShoppingCart_id", shoppingCartId).mapToDouble(CartItem::getPrice).sum();
    shoppingCart.setTotalPrice(sum);
    if(ad.getProductStatus().equals(ProductStatus.NOT_AVAILABLE))
    {
        ad.setProductStatus(ProductStatus.AVAILABLE);
    }
    ad.persist();
    shoppingCart.persist();
    return shoppingCart;
   }
   @Transactional
   public ShoppingCart removeAllItems(Long shoppingCartId){
        ShoppingCart shoppingCart=shoppingCartRepository.findById(shoppingCartId);
        List<CartItem>cartItems= cartItemRepository.stream("ShoppingCart_id", shoppingCartId).toList();
        cartItems.forEach(cartItem -> {

            Ad ad=cartItem.getAd();
            int qty=cartItem.getQuantity();
            int totalQty=qty+ ad.getQuantity();
            ad.setQuantity(totalQty);
            ad.persist();
            cartItemRepository.delete(cartItem);
        });
        shoppingCart.setTotalPrice(0);
        shoppingCart.setOrderDate(null);

        shoppingCart.persist();
        return shoppingCart;

   }
   @Transactional
   public ShoppingCart changeItemQuantity(Long cartItemId,Long shoppingCartId,Long productId,int qty) throws ResourceNotFoundException {
        ShoppingCart shoppingCart=shoppingCartRepository.findById(shoppingCartId);
        Ad ad=Ad.findById(productId);
        cartItemRepository.findByIdOptional(cartItemId).map(Item ->{
        Item.setQuantity(qty);
        double finalPrice=qty*ad.getPrice();
        Item.setPrice(finalPrice);
        ad.setQuantity(Item.getQuantity()+ad.getQuantity());
        ad.persist();
        Item.persist();
        double sum = cartItemRepository.stream("ShoppingCart_id", shoppingCartId).mapToDouble(CartItem::getPrice).sum();
        shoppingCart.setTotalPrice(sum);
        shoppingCart.persist();
        return Item;
    } ).orElseThrow(()->new ResourceNotFoundException("No Object is found"));
    return shoppingCart;
   }

}
