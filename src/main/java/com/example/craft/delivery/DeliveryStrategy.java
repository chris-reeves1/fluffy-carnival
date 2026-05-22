package com.example.craft.delivery;

import com.example.craft.domain.Order;

public interface DeliveryStrategy {
    int calculateDeliveryFee(Order order, int subtotal);
}