package com.mohamed.repositories;

import com.mohamed.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton

public class CustomerRepository implements PanacheRepository<Customer> {

    public List<Customer> findCustomerByName(String lastname) {
        return Customer.find("lastName",lastname).list();
    }

    public Optional<Customer>findCustomer(String lastname,String address){
        return Customer
                .find("lastName = :lastname and address = :address", Parameters.with("lastname",lastname).and("address",address))
                .singleResultOptional();
    }
}
