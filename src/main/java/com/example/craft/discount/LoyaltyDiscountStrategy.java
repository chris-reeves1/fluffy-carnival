package com.example.craft.discount;

import com.example.craft.domain.Order;
import com.example.craft.domain.OrderItem;

public class LoyaltyDiscountStrategy implements DiscountStrategy {

    private static final double LOYALTY_DISCOUNT_RATE = 0.12;
    private static final int SUBTOTAL_BONUS_THRESHOLD_PENCE = 7500;
    private static final int SUBTOTAL_BONUS_DISCOUNT_PENCE = 200;
    private static final int ITEM_COUNT_BONUS_THRESHOLD = 3;
    private static final int ITEM_COUNT_BONUS_DISCOUNT_PENCE = 150;
    private static final double MAX_DISCOUNT_RATE = 0.25;

    @Override
    public int calculateDiscount(Order order, int subtotal) {
        int discount = (int) (subtotal * LOYALTY_DISCOUNT_RATE);

        if (subtotal > SUBTOTAL_BONUS_THRESHOLD_PENCE) {
            discount = discount + SUBTOTAL_BONUS_DISCOUNT_PENCE;
        }

        if (countItems(order) > ITEM_COUNT_BONUS_THRESHOLD) {
            discount = discount + ITEM_COUNT_BONUS_DISCOUNT_PENCE;
        }

        int maximumDiscount = (int) (subtotal * MAX_DISCOUNT_RATE);

        return Math.min(discount, maximumDiscount);
    }

    private int countItems(Order order) {
        int itemCount = 0;

        for (OrderItem item : order.getItems()) {
            itemCount = itemCount + item.getQuantity();
        }

        return itemCount;
    }
}