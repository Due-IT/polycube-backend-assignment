package polycube.backend.unit.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polycube.backend.discount.policy.VIPDiscountPolicy;
import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.fixture.DiscountSettingFixture;

import static org.assertj.core.api.Assertions.assertThat;

public class VIPDiscountPolicyTest {

    private final VIPDiscountPolicy policy = new VIPDiscountPolicy();

    @Test
    @DisplayName("VIP 등급은 1000원이 할인되어야 한다")
    void vip_fix_discount() {
        // given
        int price = 10000;
        DiscountSetting setting = DiscountSettingFixture.createVIPDiscountSetting();

        // when
        int discount = policy.calculateDiscount(price, setting);

        // then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("원가가 1000원 미만이면 원가만큼만 할인되어야 한다 (0원 결제)")
    void low_price_limit_discount() {
        // given
        int price = 500;
        DiscountSetting setting = DiscountSettingFixture.createVIPDiscountSetting();

        // when
        int discount = policy.calculateDiscount(price, setting);

        // then
        assertThat(discount).isEqualTo(500);
    }
}
