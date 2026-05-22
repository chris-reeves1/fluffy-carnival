package com.example.craft.discount;

import com.example.craft.domain.Order;
import com.example.craft.domain.OrderItem;

public class PremiumDiscountStrategy implements DiscountStrategy {

    private static final double PREMIUM_DISCOUNT_RATE = 0.10;
    private static final int PREMIUM_ITEM_THRESHOLD = 5;
    private static final int PREMIUM_ITEM_DISCOUNT = 300;

    @Override
    public int calculateDiscount(Order order, int subtotal) {

        int discount = (int) (subtotal * PREMIUM_DISCOUNT_RATE);

        if (countItems(order) > PREMIUM_ITEM_THRESHOLD) {
            discount += PREMIUM_ITEM_DISCOUNT;
        }

        return Math.min(discount, subtotal);
    }

    int countItems(Order order) {
        int itemCount = 0;

        for (OrderItem item : order.getItems()) {
            itemCount += item.getQuantity();
        }
        return itemCount;
    }
}
