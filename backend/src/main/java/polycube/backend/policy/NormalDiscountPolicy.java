package polycube.backend.policy;

import org.springframework.stereotype.Component;
import polycube.backend.model.type.Grade;

@Component
public class NormalDiscountPolicy implements DiscountPolicy {

    @Override
    public boolean isSupport(DiscountContext context) {
        return context.grade() == Grade.NORMAL;
    }

    @Override
    public int calculateDiscount(int price) {
        return 0;
    }
}
