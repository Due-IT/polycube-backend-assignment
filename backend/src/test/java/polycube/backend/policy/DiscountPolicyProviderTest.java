package polycube.backend.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import polycube.backend.fixture.MemberFixture;
import polycube.backend.fixture.OrderFixture;
import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.model.type.Grade;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DiscountPolicyProviderTest {
    private final DiscountPolicyProvider provider = new DiscountPolicyProvider(
            List.of(
                    new VIPDiscountPolicy(),
                    new VVIPDiscountPolicy(),
                    new NormalDiscountPolicy()
            )
    );

    @Test
    @DisplayName("NORMAL 등급을 넣으면 NormalDiscountPolicy 객체를 반환해야 한다")
    void get_normal_policy() {
        Member member = MemberFixture.createMember(Grade.NORMAL);
        Order order = OrderFixture.createOrder(member);
        List<DiscountPolicy> policies = provider.getPolicies(order);
        assertThat(policies).hasOnlyElementsOfType(NormalDiscountPolicy.class);
    }

    @Test
    @DisplayName("VIP 등급을 넣으면 VIPDiscountPolicy 객체를 반환해야 한다")
    void get_vip_policy() {
        Member member = MemberFixture.createMember(Grade.VIP);
        Order order = OrderFixture.createOrder(member);
        List<DiscountPolicy> policies = provider.getPolicies(order);
        assertThat(policies).hasOnlyElementsOfType(VIPDiscountPolicy.class);
    }

    @Test
    @DisplayName("VVIP 등급을 넣으면 VVIPDiscountPolicy 객체를 반환해야 한다")
    void get_vvip_policy() {
        Member member = MemberFixture.createMember(Grade.VVIP);
        Order order = OrderFixture.createOrder(member);
        List<DiscountPolicy> policies = provider.getPolicies(order);
        assertThat(policies).hasOnlyElementsOfType(VVIPDiscountPolicy.class);
    }
}
