package com.eCommerce.services;

import com.eCommerce.dtos.OrderDto.OrderDto;
import com.eCommerce.entities.Address;
import com.eCommerce.entities.Orders;
import com.eCommerce.entities.Payment;
import com.eCommerce.entities.ShoppingCart;
import com.eCommerce.mapper.OrderMapper;
import com.eCommerce.persistence.OrderRepository;
import com.eCommerce.persistence.PaymentRepository;
import com.eCommerce.persistence.ShoppingCartRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Date;

@ApplicationScoped
public class OrderService {

    OrderMapper orderMapper=new OrderMapper();
    OrderRepository orderRepository=new OrderRepository();
    PaymentRepository paymentRepository=new PaymentRepository();
    ShoppingCartRepository shoppingCartRepository=new ShoppingCartRepository();

    public OrderDto makeOrder(Long cartId,OrderDto orderDto){
        ShoppingCart shoppingCart=shoppingCartRepository.findById(cartId);
        Payment payment=new Payment();
        orderDto.setOrderDate(new Date());
        Address billingAdress=new Address();
        billingAdress.setCity(orderDto.getCity());
        billingAdress.setState(orderDto.getState());
        billingAdress.setCountry(orderDto.getCountry());
        billingAdress.setStreet(orderDto.getStreet());
        billingAdress.setZip(orderDto.getZip());
        billingAdress.persist();
        Orders orders=orderMapper.toEntity(orderDto);
        orders.setShoppingCart(shoppingCart);
        payment.setPaymentMethod(orderDto.getPaymentMethod());
        payment.setAccountNumber(orderDto.getAccountNumber());
        payment.setCardholderName(orderDto.getCardholderName());
        payment.setCardType(orderDto.getCardType());
        payment.setCvv(orderDto.getCvv());
        payment.setExpirationDate(orderDto.getExpirationDate());
        payment.persist();
        orders.setPayment(payment);
        orders.persist();
        return orderDto;


    }
}
