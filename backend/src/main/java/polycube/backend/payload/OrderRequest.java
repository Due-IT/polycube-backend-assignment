package polycube.backend.payload;

import polycube.backend.model.PaymentMethod;

public record OrderRequest(
        Long memberId,
        String itemName,
        int price,
        PaymentMethod paymentMethod
) {
}
