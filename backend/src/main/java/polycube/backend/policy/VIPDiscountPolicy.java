package polycube.backend.policy;

import org.springframework.stereotype.Component;
import polycube.backend.model.type.Grade;

@Component
public class VIPDiscountPolicy implements DiscountPolicy {

    private static final int DISCOUNT_FIX_AMOUNT = 1000;

    @Override
    public boolean isSupport(DiscountContext context) {
        return context.grade() == Grade.VIP;
    }

    @Override
    public int calculateDiscount(int price) {
        return Math.min(DISCOUNT_FIX_AMOUNT, price);
    }
}
