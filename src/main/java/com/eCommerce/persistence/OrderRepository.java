package com.eCommerce.persistence;

import com.eCommerce.entities.Orders;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Orders> {
}
