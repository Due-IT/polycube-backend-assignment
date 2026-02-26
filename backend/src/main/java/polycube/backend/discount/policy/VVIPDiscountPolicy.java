package polycube.backend.discount.policy;

import org.springframework.stereotype.Component;
import polycube.backend.discount.DiscountContext;
import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.model.type.Grade;

@Component
public class VVIPDiscountPolicy implements DiscountPolicy {

    private static final String TARGET = Grade.VVIP.name();

    @Override
    public boolean isSupport(DiscountContext context) {
        return context.grade() == Grade.VVIP;
    }

    @Override
    public int calculateDiscount(int price, DiscountSetting setting) {
        int discountPrice = (int) (price * setting.getDiscountPercent() / 100);
        discountPrice = Math.max(discountPrice, 1000);
        return Math.min(discountPrice, price);
    }

    @Override
    public String getTarget() {
        return TARGET;
    }
}
