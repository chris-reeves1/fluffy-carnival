package com.example.craft.delivery;

import com.example.craft.domain.Order;

public class NextDayDeliveryStrategy implements DeliveryStrategy {

    private static final int NEXT_DAY_DELIVERY_FEE_PENCE = 799;
    private static final int REDUCED_NEXT_DAY_DELIVERY_FEE_PENCE = 499;
    private static final int REDUCED_DELIVERY_THRESHOLD_PENCE = 15000;

    @Override
    public int calculateDeliveryFee(Order order, int subtotal) {
        if (subtotal > REDUCED_DELIVERY_THRESHOLD_PENCE) {
            return REDUCED_NEXT_DAY_DELIVERY_FEE_PENCE;
        }

        return NEXT_DAY_DELIVERY_FEE_PENCE;
    }
}