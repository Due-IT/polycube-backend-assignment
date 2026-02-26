package polycube.backend.unit.policy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import polycube.backend.discount.DiscountPolicyProvider;
import polycube.backend.discount.policy.*;
import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.discount.setting.DiscountSettingRepository;
import polycube.backend.fixture.DiscountSettingFixture;
import polycube.backend.fixture.MemberFixture;
import polycube.backend.fixture.OrderFixture;
import polycube.backend.model.entity.Member;
import polycube.backend.model.entity.Order;
import polycube.backend.model.type.Grade;
import polycube.backend.model.type.PaymentMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class DiscountPolicyProviderTest {

    @Mock
    private DiscountSettingRepository discountSettingRepository;

    @Spy
    private List<DiscountPolicy> policies = new ArrayList<>();

    @InjectMocks
    private DiscountPolicyProvider provider;

    @BeforeEach
    void setUp() {
        policies.add(new NormalDiscountPolicy());
        policies.add(new VIPDiscountPolicy());
        policies.add(new VVIPDiscountPolicy());
        policies.add(new PointDiscountPolicy());
    }

    @Test
    @DisplayName("NORMAL 등급을 넣으면 NormalDiscountPolicy 객체를 반환해야 한다")
    void get_normal_policy() {
        // given
        Member member = MemberFixture.createMember(Grade.NORMAL);
        Order order = OrderFixture.createOrder(member);

        DiscountSetting setting = DiscountSettingFixture.createNORMALDiscountSetting();

        given(discountSettingRepository.findByPolicyName(Grade.NORMAL.name()))
                .willReturn(Optional.of(setting));

        // when
        List<AppliedDiscountPolicy> result = provider.getPolicies(order);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().policy()).isInstanceOf(NormalDiscountPolicy.class);
    }

    @Test
    @DisplayName("VIP 등급을 넣으면 VIPDiscountPolicy 객체를 반환해야 한다")
    void get_vip_policy() {
        // given
        Member member = MemberFixture.createMember(Grade.VIP);
        Order order = OrderFixture.createOrder(member);

        DiscountSetting setting = DiscountSettingFixture.createVIPDiscountSetting();

        given(discountSettingRepository.findByPolicyName(Grade.VIP.name()))
                .willReturn(Optional.of(setting));

        // when
        List<AppliedDiscountPolicy> result = provider.getPolicies(order);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().policy()).isInstanceOf(VIPDiscountPolicy.class);
    }

    @Test
    @DisplayName("VVIP 등급을 넣으면 VVIPDiscountPolicy 객체를 반환해야 한다")
    void get_vvip_policy() {
        // given
        Member member = MemberFixture.createMember(Grade.VVIP);
        Order order = OrderFixture.createOrder(member);

        DiscountSetting setting = DiscountSettingFixture.createVVIPDiscountSetting();

        given(discountSettingRepository.findByPolicyName(Grade.VVIP.name()))
                .willReturn(Optional.of(setting));

        // when
        List<AppliedDiscountPolicy> result = provider.getPolicies(order);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().policy()).isInstanceOf(VVIPDiscountPolicy.class);
    }

    @Test
    @DisplayName("POINT 결제를 넣으면 등급 할인정책과 함께 PointDiscountPolicy 객체를 반환해야 한다")
    void get_point_policy() {
        // given
        Member member = MemberFixture.createMember(Grade.NORMAL);
        Order order = OrderFixture.createOrder(member, 1000, PaymentMethod.POINT);

        DiscountSetting setting = DiscountSettingFixture.createPOINTDiscountSetting();

        given(discountSettingRepository.findByPolicyName(Grade.NORMAL.name()))
                .willReturn(Optional.of(setting));
        given(discountSettingRepository.findByPolicyName(PaymentMethod.POINT.name()))
                .willReturn(Optional.of(setting));

        // when
        List<AppliedDiscountPolicy> result = provider.getPolicies(order);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(1).policy()).isInstanceOf(PointDiscountPolicy.class);
    }
}
