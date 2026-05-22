package com.example.craft.payment;

import com.example.craft.domain.Order;

public class BankTransferPaymentProcessor implements PaymentProcessor {

    @Override
    public void processPayment(Order order, int total) {
        System.out.println("Creating bank transfer request for £" + formatPounds(total));

        if (total < 1000) {
            System.out.println("Bank transfer for low value order may not be worth processing");
        }
    }

    private String formatPounds(int pence) {
        return String.format("%.2f", pence / 100.0);
    }
}