package com.eCommerce.mapper;

import com.eCommerce.dtos.OrderDto.OrderDto;
import com.eCommerce.entities.Orders;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper implements EntityMapper<OrderDto, Orders> {
    ModelMapper mapper=new ModelMapper();
    @Override
    public Orders toEntity(OrderDto dto) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(dto,Orders.class);
    }

    @Override
    public OrderDto toDto(Orders entity) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return mapper.map(entity,OrderDto.class);
    }

    @Override
    public List<Orders> toEntity(List<OrderDto> dtoList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<Orders>orders=new ArrayList<>();
        dtoList.forEach(orderDto -> {
            orders.add(mapper.map(orderDto,Orders.class));
        });
        return orders;
    }

    @Override
    public List<OrderDto> toDto(List<Orders> entityList) {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<OrderDto>dtos=new ArrayList<>();
        entityList.forEach(orders ->{
            dtos.add(mapper.map(orders,OrderDto.class));
        } );
        return dtos;
    }
}
