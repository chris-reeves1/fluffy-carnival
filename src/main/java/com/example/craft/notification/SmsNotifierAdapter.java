package com.example.craft.notification;

import com.example.craft.domain.Customer;

public class SmsNotifierAdapter implements Notifier {

    private static final String ACCOUNT_CODE = "ORDER_SYSTEM";

    private final LegacySmsClient legacySmsClient;

    public SmsNotifierAdapter(LegacySmsClient legacySmsClient) {
        this.legacySmsClient = legacySmsClient;
    }

    @Override
    public void notifyCustomer(Customer customer, String message) {
        if (customer.getPhoneNumber() == null || customer.getPhoneNumber().isBlank()) {
            throw new IllegalArgumentException("Customer phone number is required for SMS notification");
        }

        legacySmsClient.sendTextMessage(
                customer.getPhoneNumber(),
                message,
                false,
                ACCOUNT_CODE);
    }
}