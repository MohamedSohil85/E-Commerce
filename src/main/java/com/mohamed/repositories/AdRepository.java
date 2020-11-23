package com.mohamed.repositories;

import com.mohamed.entities.Ad;
import com.mohamed.entities.Bid;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AdRepository implements PanacheRepository<Ad> {

    public List<Ad> getlist() {
        return Ad.listAll(Sort.descending("productName"));
    }
    public List<Bid> getBidList(){
        return Bid.listAll(Sort.descending("productName"));
    }

    public List<Ad> findByAdName(String productName) {
        return Ad.find("productName",productName).list();
    }
}
