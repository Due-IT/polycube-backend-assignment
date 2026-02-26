package polycube.backend.unit.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polycube.backend.discount.policy.VVIPDiscountPolicy;
import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.fixture.DiscountSettingFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class VVIPDiscountPolicyTest {

    private final VVIPDiscountPolicy policy = new VVIPDiscountPolicy();

    @Test
    @DisplayName("VVIP 등급은 10% 할인이 적용되어야 한다")
    void vvip_rate_discount() {
        // given
        int price = 20000;
        DiscountSetting setting = DiscountSettingFixture.createVVIPDiscountSetting();

        // when
        int discount = policy.calculateDiscount(price, setting);

        // then
        assertThat(discount).isEqualTo(2000);
    }

    @Test
    @DisplayName("VVIP 할인 금액이 1000원 미만이면 최소 1000원을 할인해야 한다")
    void vvip_minimum_discount_limit() {
        // given
        int price = 5000;
        DiscountSetting setting = DiscountSettingFixture.createVVIPDiscountSetting();

        // when
        int discount = policy.calculateDiscount(price, setting);

        // then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("원가가 1000원 미만이면 원가만큼만 할인되어야 한다 (0원 결제)")
    void vvip_low_price_limit_discount() {
        // given
        int price = 800;
        DiscountSetting setting = DiscountSettingFixture.createVVIPDiscountSetting();

        // when
        int discount = policy.calculateDiscount(price, setting);

        // then
        assertThat(discount).isEqualTo(800);
    }
}
