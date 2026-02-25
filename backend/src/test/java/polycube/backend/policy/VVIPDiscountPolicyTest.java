package polycube.backend.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polycube.backend.model.type.Grade;

import static org.assertj.core.api.Assertions.assertThat;

public class VVIPDiscountPolicyTest {

    private final VVIPDiscountPolicy policy = new VVIPDiscountPolicy();

    @Test
    @DisplayName("VVIP 등급은 10% 할인이 적용되어야 한다")
    void vvip_rate_discount() {
        // given
        Grade grade = Grade.VVIP;
        int price = 20000;

        // when
        int discount = policy.calculateDiscount(grade, price);

        // then
        assertThat(discount).isEqualTo(2000);
    }
}
