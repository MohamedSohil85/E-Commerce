package com.eCommerce.persistence;

import com.eCommerce.entities.WishItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WishItemRepository implements PanacheRepository<WishItem> {
}
