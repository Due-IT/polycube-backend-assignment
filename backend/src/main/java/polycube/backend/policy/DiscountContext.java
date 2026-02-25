package polycube.backend.policy;

import polycube.backend.model.type.Grade;
import polycube.backend.model.type.PaymentMethod;

public record DiscountContext(
        Grade grade,
        PaymentMethod paymentMethod
) {
}
