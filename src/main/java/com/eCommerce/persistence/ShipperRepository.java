package com.eCommerce.persistence;

import com.eCommerce.entities.Shipper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShipperRepository implements PanacheRepository<Shipper> {
}
