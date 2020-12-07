package com.mohamed.repositories;

import com.mohamed.entities.ShoppingCart;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class ShoppingCartRepository implements PanacheRepository<ShoppingCart> {
    public Optional<ShoppingCart>findShoppingCartByCustomerName(Long id){
        return ShoppingCart.find("SELECT id FROM shoppingcart WHERE = :customer_id", Parameters.with("customer_id",id)).singleResultOptional();

    }
    public Optional<ShoppingCart>findCartByToken(String token){
        return ShoppingCart.find("cartCode",token).singleResultOptional();
    }
}
