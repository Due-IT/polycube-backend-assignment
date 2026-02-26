package polycube.backend.discount.policy;

import polycube.backend.discount.DiscountContext;
import polycube.backend.discount.setting.DiscountSetting;

public interface DiscountPolicy {

    boolean isSupport(DiscountContext context);

    int calculateDiscount(int price, DiscountSetting setting);

    String getTarget();
}
