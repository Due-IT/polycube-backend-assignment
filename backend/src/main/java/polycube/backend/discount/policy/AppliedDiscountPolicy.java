package polycube.backend.discount.policy;

import polycube.backend.discount.setting.DiscountSetting;

public record AppliedDiscountPolicy(
        DiscountPolicy policy,
        DiscountSetting setting
) {
    public int calculateDiscount(int price) {
        return policy.calculateDiscount(price, setting);
    }

    public int getPriority() {
        return setting.getPriority();
    }
}
