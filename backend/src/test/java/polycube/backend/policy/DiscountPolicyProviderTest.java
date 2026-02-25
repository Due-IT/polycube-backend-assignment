package polycube.backend.policy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        Grade grade = Grade.NORMAL;
        DiscountPolicy policy = provider.getPolicy(grade);
        assertThat(policy).isExactlyInstanceOf(NormalDiscountPolicy.class);
    }

    @Test
    @DisplayName("VIP 등급을 넣으면 VIPDiscountPolicy 객체를 반환해야 한다")
    void get_vip_policy() {
        Grade grade = Grade.VIP;
        DiscountPolicy policy = provider.getPolicy(grade);
        assertThat(policy).isExactlyInstanceOf(VIPDiscountPolicy.class);
    }

    @Test
    @DisplayName("VVIP 등급을 넣으면 VVIPDiscountPolicy 객체를 반환해야 한다")
    void get_vvip_policy() {
        Grade grade = Grade.VVIP;
        DiscountPolicy policy = provider.getPolicy(grade);
        assertThat(policy).isExactlyInstanceOf(VVIPDiscountPolicy.class);
    }
}
