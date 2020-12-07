package com.mohamed.repositories;

import com.mohamed.entities.Ad;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AdRepository implements PanacheRepository<Ad> {

    public List<Ad> getlist() {
        return Ad.listAll(Sort.descending("productName"));
    }

    public List<Ad> findByAdName(String productName) {
        return Ad.find("productName",productName).list();
    }
    public List<Ad> findByKeyword(String name){
        return Ad.find("SELECT * FROM AD WHERE productName = :keyword", Parameters.with("keyword",name)).list();
    }
    public Optional<Ad> findAdByName(String name){
        return Ad.find("productName",name).singleResultOptional();
    }

}
