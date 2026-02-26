package polycube.backend.discount.policy;

import org.springframework.stereotype.Component;
import polycube.backend.discount.DiscountContext;
import polycube.backend.discount.setting.DiscountSetting;
import polycube.backend.model.type.PaymentMethod;

@Component
public class PointDiscountPolicy implements DiscountPolicy {

    private static final String TARGET = PaymentMethod.POINT.name();

    @Override
    public boolean isSupport(DiscountContext context) {
        return TARGET.equals(context.paymentMethod().name());
    }

    @Override
    public int calculateDiscount(int price, DiscountSetting setting) {
        return (int) (price * setting.getDiscountPercent() / 100);
    }

    @Override
    public String getTarget() {
        return TARGET;
    }
}
