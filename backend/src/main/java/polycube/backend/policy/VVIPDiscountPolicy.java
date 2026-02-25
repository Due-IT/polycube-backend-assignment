package polycube.backend.policy;

import org.springframework.stereotype.Component;
import polycube.backend.model.type.Grade;

@Component
public class VVIPDiscountPolicy implements DiscountPolicy {

    private static final double DISCOUNT_RATE = 0.1;
    private static final int MINIMUM_DISCOUNT = 1000;

    @Override
    public int calculateDiscount(Grade grade, int price) {
        if (grade != Grade.VVIP) {
            return 0;
        }

        int discountAmount = (int) (price * DISCOUNT_RATE);
        discountAmount = Math.max(discountAmount, MINIMUM_DISCOUNT);
        return Math.min(discountAmount, price);
    }
}
