package com.mohamed.repositories;

import com.mohamed.entities.Bid;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BidRepository implements PanacheRepository<Bid> {
    public Optional<Bid> findBidByName(String productName){
        return Bid.find("productName",productName).singleResultOptional();
    }
    public List<Bid> getBidList(){
        return Bid.listAll(Sort.descending("productName"));
    }
}
