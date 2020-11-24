package com.mohamed.repositories;

import com.mohamed.entities.Supplier;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SupplierRepository implements PanacheRepository<Supplier> {
}
