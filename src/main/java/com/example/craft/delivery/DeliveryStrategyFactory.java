package com.example.craft.delivery;

public class DeliveryStrategyFactory {

    public DeliveryStrategy getStrategy(String deliveryType) {
        if (deliveryType == null || deliveryType.isBlank()) {
            throw new IllegalArgumentException("Delivery type is required");
        }

        return switch (deliveryType.trim().toUpperCase()) {
            case "STANDARD" -> new StandardDeliveryStrategy();
            case "NEXT_DAY" -> new NextDayDeliveryStrategy();
            case "COLLECTION" -> new CollectionDeliveryStrategy();
            default -> throw new IllegalArgumentException("Unknown delivery type: " + deliveryType);
        };
    }
}