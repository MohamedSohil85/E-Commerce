package com.mohamed.repositories;

import com.mohamed.entities.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {
}
