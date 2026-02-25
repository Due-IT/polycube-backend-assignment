package polycube.backend.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polycube.backend.model.type.Grade;

import static org.assertj.core.api.Assertions.assertThat;

public class NormalDiscountPolicyTest {

    private final NormalDiscountPolicy policy = new NormalDiscountPolicy();

    @Test
    @DisplayName("NORMAL 등급은 0원이 할인된다")
    void normal_grade_no_discount() {
        // given
        int price = 10000;

        // when
        int discount = policy.calculateDiscount(price);

        // then
        assertThat(discount).isEqualTo(0);
    }
}
