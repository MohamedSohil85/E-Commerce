package com.mohamed.endpoints;

import com.mohamed.entities.Customer;
import com.mohamed.repositories.CustomerRepository;
import io.quarkus.panache.common.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;


@RestController
public class CustomerEndpoint {
    @Inject
    CustomerRepository customerRepository;

    @GetMapping(value = "/customers",produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity loadCustomers(){
      List<Customer> customerList= (List<Customer>) customerRepository.findAll(Sort.ascending("lastName"));
      return new ResponseEntity(customerList, HttpStatus.FOUND);
    }

}
