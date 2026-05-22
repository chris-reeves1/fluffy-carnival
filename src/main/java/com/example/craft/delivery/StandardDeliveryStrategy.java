package com.example.craft.delivery;

import com.example.craft.domain.Order;

public class StandardDeliveryStrategy implements DeliveryStrategy {

    private static final int STANDARD_DELIVERY_FEE_PENCE = 399;
    private static final int FREE_DELIVERY_THRESHOLD_PENCE = 5000;

    @Override
    public int calculateDeliveryFee(Order order, int subtotal) {
        if (subtotal > FREE_DELIVERY_THRESHOLD_PENCE) {
            return 0;
        }

        return STANDARD_DELIVERY_FEE_PENCE;
    }
}