package com.mohamed.repositories;

import com.mohamed.entities.Request;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RequestRepository implements PanacheRepository<Request> {
}
