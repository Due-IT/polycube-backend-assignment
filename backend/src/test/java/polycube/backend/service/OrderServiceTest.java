package polycube.backend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    @Nested
    @DisplayName("회원 등급별 할인 정책 테스트")
    class DiscountPolicyTest {

        @Test
        @DisplayName("NORMAL 등급은 할인이 적용되지 않아야 한다")
        void normal_member_no_discount() {
            // given
            Member member = new Member(1L, "일반사용자", Grade.NORMAL);
            int price = 10000;

            // when
            Order order = new Order(member, "상품A", price);

            // then
            assertThat(order.getDiscountPrice()).isEqualTo(0);
            assertThat(order.calculateFinalPrice()).isEqualTo(10000);
        }

        @Test
        @DisplayName("VIP 등급은 1,000원 고정 금액 할인이 적용되어야 한다")
        void vip_member_fixed_discount() {
            // given
            Member member = new Member(2L, "VIP사용자", Grade.VIP);
            int price = 10000;

            // when
            Order order = new Order(member, "상품B", price);

            // then
            assertThat(order.getDiscountPrice()).isEqualTo(1000);
            assertThat(order.calculateFinalPrice()).isEqualTo(9000);
        }

        @Test
        @DisplayName("VVIP 등급은 주문 금액의 10% 할인이 적용되어야 한다")
        void vvip_member_rate_discount() {
            // given
            Member member = new Member(3L, "VVIP사용자", Grade.VVIP);
            int price = 20000;

            // when
            Order order = new Order(member, "상품C", price);

            // then
            // 20,000원의 10%인 2,000원 할인 확인
            assertThat(order.getDiscountPrice()).isEqualTo(2000);
            assertThat(order.calculateFinalPrice()).isEqualTo(18000);
        }
    }
}
