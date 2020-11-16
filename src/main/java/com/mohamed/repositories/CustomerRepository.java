package com.mohamed.repositories;

import com.mohamed.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton

public class CustomerRepository implements PanacheRepository<Customer> {

    public List<Customer> findCustomerByName(String lastname) {
        return find("SELECT * FROM CUSTOMER WHERE :=lastname",lastname).list();
    }

}
