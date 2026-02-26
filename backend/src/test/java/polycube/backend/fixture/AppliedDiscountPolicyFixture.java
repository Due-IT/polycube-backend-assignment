package polycube.backend.fixture;

import polycube.backend.discount.policy.AppliedDiscountPolicy;
import polycube.backend.discount.policy.DiscountPolicy;
import polycube.backend.discount.policy.VIPDiscountPolicy;
import polycube.backend.discount.setting.DiscountSetting;

public class AppliedDiscountPolicyFixture {
    public static AppliedDiscountPolicy createVIPAppliedPolicy() {
        return createAppliedDiscountPolicy(
                new VIPDiscountPolicy(),
                DiscountSettingFixture.createVIPDiscountSetting()
        );
    }

    private static AppliedDiscountPolicy createAppliedDiscountPolicy(DiscountPolicy policy, DiscountSetting setting) {
        return new AppliedDiscountPolicy(policy, setting);
    }
}
