package polycube.backend.discount.policy;

import org.springframework.stereotype.Component;
import polycube.backend.discount.DiscountContext;
import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.model.type.Grade;

@Component
public class NormalDiscountPolicy implements DiscountPolicy {

    private static final String TARGET = Grade.NORMAL.name();

    @Override
    public boolean isSupport(DiscountContext context) {
        return context.grade() == Grade.NORMAL;
    }

    @Override
    public int calculateDiscount(int price, DiscountSetting setting) {
        return Math.min(price, setting.getDiscountAmount());
    }

    @Override
    public String getTarget() {
        return TARGET;
    }
}
