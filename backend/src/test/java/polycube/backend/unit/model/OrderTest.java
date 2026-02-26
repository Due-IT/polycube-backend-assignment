package polycube.backend.unit.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polycube.backend.discount.policy.AppliedDiscountPolicy;
import polycube.backend.fixture.AppliedDiscountPolicyFixture;
import polycube.backend.fixture.OrderFixture;
import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.model.type.Grade;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    @DisplayName("할인 정책을 적용하면 주문의 할인 금액이 올바르게 설정되어야 한다")
    void apply_discount_test() {
        // given
        Member member = new Member(1L, "VIP회원", Grade.VIP);
        Order order = OrderFixture.createOrder(member, 10000);
        AppliedDiscountPolicy applied = AppliedDiscountPolicyFixture.createVIPAppliedPolicy();

        // when
        order.applyDiscounts(List.of(applied));

        // then
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("pay 메서드를 호출하면 할인액이 반영된 결제 객체가 생성되어야 한다")
    void create_payment_test() {
        // given
        Member member = new Member(1L, "VIP회원", Grade.VIP);
        Order order = OrderFixture.createOrder(member, 10000);
        AppliedDiscountPolicy applied = AppliedDiscountPolicyFixture.createVIPAppliedPolicy();
        order.applyDiscounts(List.of(applied));

        // when
        order.pay();

        // then
        assertThat(order.getFinalPrice()).isEqualTo(9000);
        assertThat(order.getPaidGrade()).isEqualTo(Grade.VIP);
    }
}
