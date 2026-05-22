package com.example.craft.payment;

import com.example.craft.domain.Order;

public class PayPalPaymentProcessor implements PaymentProcessor {

    @Override
    public void processPayment(Order order, int total) {
        System.out.println("Taking PayPal payment for £" + formatPounds(total));

        if (order.getCustomer().getEmail().endsWith("@example.com")) {
            System.out.println("PayPal payment using test-like email address");
        }
    }

    private String formatPounds(int pence) {
        return String.format("%.2f", pence / 100.0);
    }
}