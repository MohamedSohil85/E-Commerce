package com.eCommerce.persistence;

import com.eCommerce.entities.Vendor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VendorRepository implements PanacheRepository<Vendor> {

  public   Optional<Vendor>findVendorByEmail(String email){
        return Vendor.find("email",email).singleResultOptional();
    }
  public   Optional<Vendor>findByToken(String token){
        return Vendor.find("token",token).singleResultOptional();
    }
    public List<Vendor>getAllVendorsByName(String lastName){
      return list("lastName",lastName);
    }
}
