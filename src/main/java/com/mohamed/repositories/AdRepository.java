package com.mohamed.repositories;

import com.mohamed.entities.Ad;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AdRepository implements PanacheRepository<Ad> {
}
