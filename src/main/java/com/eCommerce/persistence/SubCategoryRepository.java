package com.eCommerce.persistence;


import com.eCommerce.entities.SubCategory;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SubCategoryRepository implements PanacheRepository<SubCategory> {
}
