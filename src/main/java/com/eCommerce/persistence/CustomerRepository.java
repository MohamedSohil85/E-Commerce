package com.eCommerce.persistence;
import com.eCommerce.entities.Customer;
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

    public Optional<Customer>findCustomerByToken(String token){
        return Customer
                .find("token = : token", Parameters.with("token",token))
                .singleResultOptional();
    }
    public List<Customer>findCustomersByKeyword(String keyword){
        return Customer.find("lastName LIKE CONCAT('%',:keyword)", Parameters.with("keyword",keyword)).list();
    }
    public Optional<Customer>findByLastName(String name){
        return Customer.find("lastName",name).singleResultOptional();
    }
    public List<Customer>getCustomers(){
        return Customer.listAll(Sort.descending("lastName"));
    }
    public Optional<Customer>findCustomerByEmail(String email){
        return Customer.find("email",email).singleResultOptional();
    }
}
