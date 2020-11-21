package com.mohamed.repositories;

import com.mohamed.entities.ShoppingCart;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShoppingCartRepository implements PanacheRepository<ShoppingCart> {
}
