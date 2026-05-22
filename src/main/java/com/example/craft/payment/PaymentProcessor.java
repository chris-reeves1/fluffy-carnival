package com.example.craft.payment;

import com.example.craft.domain.Order;

public interface PaymentProcessor {
    void processPayment(Order order, int total);
}