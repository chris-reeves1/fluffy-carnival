package com.example.craft.delivery;

import com.example.craft.domain.Order;

public class CollectionDeliveryStrategy implements DeliveryStrategy {

    @Override
    public int calculateDeliveryFee(Order order, int subtotal) {
        if (order.getCustomer().getPhoneNumber() == null) {
            System.out.println("Collection selected but no phone number was provided");
        }

        return 0;
    }
}