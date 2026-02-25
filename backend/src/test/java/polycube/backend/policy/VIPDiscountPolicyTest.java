package polycube.backend.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polycube.backend.model.type.Grade;

public class VIPDiscountPolicyTest {

    private final VIPDiscountPolicy policy = new VIPDiscountPolicy();

    @Test
    @DisplayName("VIP 등급은 1000원이 할인되어야 한다")
    void vip_fix_discount() {
        // given
        Grade grade = Grade.VIP;
        int price = 10000;

        // when
        int discount = policy.calculateDiscount(grade, price);

        // then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("원가가 1000원 미만이면 원가만큼만 할인되어야 한다 (0원 결제)")
    void low_price_limit_discount() {
        // given
        Grade grade = Grade.VIP;
        int price = 500;

        // when
        int discount = policy.calculateDiscount(grade, price);

        // then
        assertThat(discount).isEqualTo(500);
    }
}
