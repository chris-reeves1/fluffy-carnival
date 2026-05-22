package com.example.craft.payment;

import com.example.craft.domain.Order;

public class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public void processPayment(Order order, int total) {
        System.out.println("Taking card payment for £" + formatPounds(total));

        if (total > 100000) {
            System.out.println("Large card payment requires manual review");
        }
    }

    private String formatPounds(int pence) {
        return String.format("%.2f", pence / 100.0);
    }
}