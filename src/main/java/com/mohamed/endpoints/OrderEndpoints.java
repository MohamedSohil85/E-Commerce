package com.mohamed.endpoints;

import com.mohamed.repositories.OrderRepository;
import lombok.ToString;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/api")
public class OrderEndpoints {
    @Inject
    OrderRepository orderRepository;

}
