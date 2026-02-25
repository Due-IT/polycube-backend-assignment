package polycube.backend.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polycube.backend.fixture.OrderFixture;
import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.model.entity.Payment;
import polycube.backend.model.type.Grade;
import polycube.backend.model.type.PaymentMethod;
import polycube.backend.policy.DiscountPolicy;
import polycube.backend.policy.PointDiscountPolicy;
import polycube.backend.policy.VIPDiscountPolicy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest {

    @Test
    @DisplayName("할인 정책을 적용하면 주문의 할인 금액이 올바르게 설정되어야 한다")
    void apply_discount_test() {
        // given
        Member member = new Member(1L, "VIP회원", Grade.VIP);
        Order order = OrderFixture.createOrder(member, 10000);

        // when
        order.applyDiscounts(List.of(new VIPDiscountPolicy()));

        // then
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

    @Test
    @DisplayName("pay 메서드를 호출하면 할인액이 반영된 결제 객체가 생성되어야 한다")
    void create_payment_test() {
        // given
        Member member = new Member(1L, "VIP회원", Grade.VIP);
        Order order = OrderFixture.createOrder(member, 10000);
        order.applyDiscounts(List.of(new VIPDiscountPolicy()));

        // when
        Payment payment = order.pay(PaymentMethod.CREDIT_CARD);

        // then
        assertThat(payment.getFinalAmount()).isEqualTo(9000);
        assertThat(payment.getPaidGrade()).isEqualTo(Grade.VIP);
    }

    @Test
    @DisplayName("등급 할인과 결제 수단 할인이 중복 적용될 때 우선순위가 지켜져야 한다")
    void double_discount_priority_test() {
        // given
        Member member = new Member(1L, "VIP 회원", Grade.VVIP);
        Order order = OrderFixture.createOrder(member, 10000, PaymentMethod.POINT);

        List<DiscountPolicy> policies = List.of(
                new VIPDiscountPolicy(),     // Priority: 1
                new PointDiscountPolicy()   // Priority: 2
        );

        // when
        order.applyDiscounts(policies);

        // then
        assertThat(order.getDiscountPrice()).isEqualTo(1450);
        assertThat(order.calculateFinalPrice()).isEqualTo(8550);
    }
}
