package com.eCommerce.persistence;

import com.eCommerce.entities.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<Category> {
    public Optional<Category> findCategoryByName(String name) {
        return Category.find("categoryName",name).singleResultOptional();
    }
}
