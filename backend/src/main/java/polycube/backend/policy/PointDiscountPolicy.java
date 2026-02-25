package polycube.backend.policy;

import polycube.backend.model.type.PaymentMethod;

public class PointDiscountPolicy implements DiscountPolicy {

    private static final double DISCOUNT_RATE = 0.05;
    private static final int DISCOUNT_PRIORITY = 2;

    @Override
    public boolean isSupport(DiscountContext context) {
        return context.paymentMethod() == PaymentMethod.POINT;
    }

    @Override
    public int calculateDiscount(int price) {
        return (int) (price * DISCOUNT_RATE);
    }

    @Override
    public int getPriority() {
        return 2;
    }
}
