package com.eCommerce.persistence;

import com.eCommerce.entities.Ad;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AdRepository implements PanacheRepository<Ad> {

    public List<Ad> getListOfAds() {
        return Ad.listAll(Sort.descending("productName"));
    }


    public List<Ad> findByKeyword(String name){
        return Ad.find("SELECT * FROM AD WHERE productName = :keyword", Parameters.with("keyword",name)).list();
    }
    public List<Ad> findAdByName(String name){
        return Ad.find("productName",name).list();
    }

}
