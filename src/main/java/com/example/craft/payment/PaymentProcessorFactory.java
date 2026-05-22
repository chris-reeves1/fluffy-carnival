package com.example.craft.payment;

public class PaymentProcessorFactory {

    public PaymentProcessor getProcessor(String paymentType) {
        if (paymentType == null || paymentType.isBlank()) {
            throw new IllegalArgumentException("Payment type is required");
        }

        return switch (paymentType.trim().toUpperCase()) {
            case "CARD" -> new CardPaymentProcessor();
            case "PAYPAL" -> new PayPalPaymentProcessor();
            case "BANK_TRANSFER" -> new BankTransferPaymentProcessor();
            default -> throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        };
    }
}