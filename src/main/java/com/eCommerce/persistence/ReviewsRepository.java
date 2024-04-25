package com.eCommerce.persistence;

import com.eCommerce.entities.Reviews;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReviewsRepository implements PanacheRepository<Reviews> {
}
