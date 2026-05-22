package com.example.craft.discount;

import com.example.craft.domain.Order;

public class StaffDiscountStrategy implements DiscountStrategy {

    private static final double STAFF_DISCOUNT_RATE = 0.20;
    private static final int STAFF_BONUS_THRESHOLD = 20000;
    private static final int STAFF_BONUS_DISCOUNT = 500;

    @Override
    public int calculateDiscount(Order order, int subtotal) {

        int discount = (int) (subtotal * STAFF_DISCOUNT_RATE);

        if (subtotal > STAFF_BONUS_THRESHOLD) {
            discount += STAFF_BONUS_DISCOUNT;
        }

        return Math.min(discount, subtotal);
    }

}
