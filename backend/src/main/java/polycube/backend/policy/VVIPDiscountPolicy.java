package polycube.backend.policy;

import org.springframework.stereotype.Component;
import polycube.backend.model.type.Grade;

@Component
public class VVIPDiscountPolicy implements DiscountPolicy {

    private static final double DISCOUNT_RATE = 0.1;
    private static final int MINIMUM_DISCOUNT = 1000;

    @Override
    public boolean isSupport(Grade grade) {
        return grade == Grade.VVIP;
    }

    @Override
    public int calculateDiscount(Grade grade, int price) {
        int discountPrice = (int) (price * DISCOUNT_RATE);
        discountPrice = Math.max(discountPrice, MINIMUM_DISCOUNT);
        return Math.min(discountPrice, price);
    }
}
