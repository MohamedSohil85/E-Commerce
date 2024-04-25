package com.eCommerce.mapper;

import com.eCommerce.dtos.accountdto.CustomerDto;
import com.eCommerce.entities.Customer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class CustomerMapper implements EntityMapper<CustomerDto, Customer> {

    private ModelMapper mapper=new ModelMapper();
    @Override
    public Customer toEntity(CustomerDto dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(dto,Customer.class);
    }

    @Override
    public CustomerDto toDto(Customer entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(entity,CustomerDto.class);
    }

    @Override
    public List<Customer> toEntity(List<CustomerDto> dtoList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<Customer>customers=new ArrayList<>();
        dtoList.forEach(customerDto -> {
            customers.add(mapper.map(customerDto,Customer.class));
        });
        return customers;
    }

    @Override
    public List<CustomerDto> toDto(List<Customer> entityList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<CustomerDto>customers=new ArrayList<>();
        entityList.forEach(customer -> {
            customers.add(mapper.map(customer,CustomerDto.class));
        });
        return customers;
    }
}
