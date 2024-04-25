package com.eCommerce.services;

import com.eCommerce.persistence.PaymentRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentService {

    PaymentRepository paymentRepository;
}
