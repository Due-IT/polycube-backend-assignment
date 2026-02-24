package polycube.backend.payload;

import polycube.backend.model.type.PaymentMethod;

import java.time.LocalDateTime;

public record OrderResponse(
    String status,
    String message,
    int finalAmount,
    PaymentMethod paymentMethod,
    LocalDateTime orderDateTime
) {
}
