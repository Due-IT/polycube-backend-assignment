package polycube.backend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import polycube.backend.model.Grade;

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

        @Nested
        @DisplayName("VVIP 등급 할인 정책 테스트")
        class VVIPDiscountTest {
            @Test
            @DisplayName("VVIP는 할인 금액이 1,000원보다 적으면 1,000원을 할인받는다")
            void vvip_minimum_discount_test() {
                // given: 5,000원 상품 구매 (10%는 500원이지만 1,000원 할인 적용되어야 함)
                Member member = new Member(5L, "소액결제VVIP", Grade.VVIP);
                int price = 5000;

                // when
                Order order = new Order(member, "저렴한상품", price);

                // then
                assertThat(order.getDiscountPrice()).isEqualTo(1000);
                assertThat(order.calculateFinalPrice()).isEqualTo(4000);
            }

            @Test
            @DisplayName("VVIP는 할인 금액이 1,000원보다 크면 10% 할인이 적용되어야 한다")
            void vvip_normal_rate_discount_test() {
                // given: 20,000원 상품 구매 (10%는 2,000원이므로 1,000원보다 큼)
                Member member = new Member(6L, "고액결제VVIP", Grade.VVIP);
                int price = 20000;

                // when
                Order order = new Order(member, "비싼상품", price);

                // then
                assertThat(order.getDiscountPrice()).isEqualTo(2000);
                assertThat(order.calculateFinalPrice()).isEqualTo(18000);
            }
        }
    }
}
