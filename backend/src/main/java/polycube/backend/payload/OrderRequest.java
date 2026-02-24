package polycube.backend.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import polycube.backend.model.PaymentMethod;

public record OrderRequest(
        @NotNull(message = "회원 식별자는 필수입니다.")
        Long memberId,

        @NotBlank(message = "상품명은 비어있을 수 없습니다.")
        String itemName,

        @Min(value = 0, message = "상품 가격은 0원 이상이어야 합니다.")
        int price,

        @NotNull(message = "결제 수단을 선택해주세요.")
        PaymentMethod paymentMethod
) {
}
