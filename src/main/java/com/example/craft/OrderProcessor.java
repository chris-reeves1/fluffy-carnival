package com.example.craft;

import com.example.craft.discount.DiscountStrategy;
import com.example.craft.discount.DiscountStrategyFactory;
import com.example.craft.domain.Customer;
import com.example.craft.domain.CustomerType;
import com.example.craft.domain.Order;
import com.example.craft.domain.OrderItem;

import com.example.craft.notification.ConsoleNotifier;
import com.example.craft.notification.LegacySmsClient;
import com.example.craft.notification.Notifier;
import com.example.craft.notification.SmsNotifierAdapter;

import com.example.craft.delivery.DeliveryStrategy;
import com.example.craft.delivery.DeliveryStrategyFactory;


public class OrderProcessor {

    private final DiscountStrategyFactory discountStrategyFactory = new DiscountStrategyFactory();
    private final Notifier emailNotifier = new ConsoleNotifier();
    private final Notifier smsNotifier = new SmsNotifierAdapter(new LegacySmsClient());
    private final DeliveryStrategyFactory deliveryStrategyFactory = new DeliveryStrategyFactory();


    public String process(Order order) {
        validateOrder(order);

        int subtotal = calculateSubtotal(order);
        int discount = calculateDiscount(order.getCustomer(), order, subtotal);
        int deliveryFee = calculateDeliveryFee(order, subtotal);

        int total = subtotal - discount + deliveryFee;

        if (total <= 0) {
            throw new IllegalStateException("Order total must be positive");
        }

        processPayment(order, total);
        sendNotifications(order, total);

        return generateReceipt(order, subtotal, discount, deliveryFee, total);
    }

    private void validateOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order must not be null");
        }

        if (order.getCustomer() == null) {
            throw new IllegalArgumentException("Customer must not be null");
        }

        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        Customer customer = order.getCustomer();

        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is invalid");
        }

        if (customer.getEmail() == null || !customer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Customer email is invalid");
        }

        if (customer.getType() == null) {
            throw new IllegalArgumentException("Customer type is required");
        }
        for (OrderItem item : order.getItems()) {
            if (item == null) {
                throw new IllegalArgumentException("Order item must not be null");
            }

            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Item quantity must be positive");
            }

            if (item.getUnitPricePence() <= 0) {
                throw new IllegalArgumentException("Item price must be positive");
            }
        }
    }

    private int calculateSubtotal(Order order) {
        int subtotal = 0;

        for (OrderItem item : order.getItems()) {

            subtotal = subtotal + item.getQuantity() * item.getUnitPricePence();

        }
        return subtotal;
    }

    private int calculateDiscount(Customer customer, Order order, int subtotal) {
        DiscountStrategy strategy = discountStrategyFactory.getStrategy(customer.getType());
        return strategy.calculateDiscount(order, subtotal);
    }

    private int calculateDeliveryFee(Order order, int subtotal) {
        DeliveryStrategy strategy = deliveryStrategyFactory.getStrategy(order.getDeliveryType());
        return strategy.calculateDeliveryFee(order, subtotal);
    }

    // private int calculateDeliveryFee(Order order, int subtotal) {
    //     String type = order.getDeliveryType();
    //     Customer customer = order.getCustomer();

    //     if (type.equalsIgnoreCase("STANDARD")) {
    //         System.out.println("Standard delivery selected");
    //         return subtotal > 5000 ? 0 : 399;

    //     } else if (type.equalsIgnoreCase("NEXT_DAY")) {
    //         System.out.println("Next day delivery selected");
    //         return subtotal > 15000 ? 499 : 799;

    //     } else if (type.equalsIgnoreCase("COLLECTION")) {
    //         System.out.println("Collection selected");

    //         if (customer.getPhoneNumber() == null) {
    //             System.out.println("Collection selected but no phone number was provided");
    //         }

    //         return 0;

    //     } else {
    //         throw new IllegalArgumentException("Unknown delivery type: " + type);
    //     }
    // }

    private void processPayment(Order order, int total) {
        Customer customer = order.getCustomer();
        String paymentType = order.getPaymentType();

        if (paymentType.equalsIgnoreCase("CARD")) {
            System.out.println("Taking card payment for £" + formatPounds(total));

            if (total > 100000) {
                System.out.println("Large card payment requires manual review");
            }

        } else if (paymentType.equalsIgnoreCase("PAYPAL")) {
            System.out.println("Taking PayPal payment for £" + formatPounds(total));

            if (customer.getEmail().endsWith("@example.com")) {
                System.out.println("PayPal payment using test-like email address");
            }

        } else if (paymentType.equalsIgnoreCase("BANK_TRANSFER")) {
            System.out.println("Creating bank transfer request for £" + formatPounds(total));

            if (total < 1000) {
                System.out.println("Bank transfer for low value order may not be worth processing");
            }

        } else {
            throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }
    }

    private void sendNotifications(Order order, int total) {
        Customer customer = order.getCustomer();

        System.out.println("Saving order " + order.getOrderId());
        System.out.println("Saving order " + order.getOrderId() + " for customer " + customer.getName());

        String message = "Dear " + customer.getName()
                + ", your order " + order.getOrderId()
                + " has been processed. Total: £" + formatPounds(total);

        emailNotifier.notifyCustomer(customer, message);

        if (customer.getPhoneNumber() != null && customer.getPhoneNumber().startsWith("07")) {
            smsNotifier.notifyCustomer(customer, "Order " + order.getOrderId() + " confirmed by SMS");
        }

        if (customer.getType() == CustomerType.PREMIUM && total > 5000) {
            emailNotifier.notifyCustomer(customer, "Thank you for being a premium customer.");
        }
    }

    private String generateReceipt(Order order, int subtotal, int discount, int deliveryFee, int total) {
        String receipt = "Receipt\n"
                + "-------\n"
                + "Order: " + order.getOrderId() + "\n"
                + "Customer: " + order.getCustomer().getName() + "\n"
                + "Subtotal: £" + formatPounds(subtotal) + "\n"
                + "Discount: £" + formatPounds(discount) + "\n"
                + "Delivery: £" + formatPounds(deliveryFee) + "\n"
                + "Total: £" + formatPounds(total) + "\n";

        System.out.println(receipt);
        return receipt;
    }

    private String formatPounds(int pence) {
        return String.format("%.2f", pence / 100.0);
    }
}
