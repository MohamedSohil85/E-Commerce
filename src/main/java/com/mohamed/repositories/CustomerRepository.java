package com.mohamed.repositories;
import com.mohamed.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public List<Customer> findCustomerByName(String lastname) {
        return Customer.find("lastName",lastname).list();
    }

    public Optional<Customer>findCustomer(String lastname,String address){
        return Customer
                .find("lastName = :lastname and address = :address", Parameters.with("lastname",lastname).and("address",address))
                .singleResultOptional();
    }
    public List<Customer>findCustomersByKeyword(String keyword){
        return Customer.find("SELECT * FROM CUSTMER WHERE lastName LIKE = :keyword", Parameters.with("keyword",keyword)).list();
    }
    public Optional<Customer>findBylastName(String name){
        return Customer.find("lastName",name).singleResultOptional();
    }
    public List<Customer>getCusttomers(){
        return Customer.listAll(Sort.descending("lastName"));
    }
}
