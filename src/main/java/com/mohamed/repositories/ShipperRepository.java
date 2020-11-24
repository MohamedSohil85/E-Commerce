package com.mohamed.repositories;

import com.mohamed.entities.Shipper;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ShipperRepository implements PanacheRepository<Shipper> {
}
